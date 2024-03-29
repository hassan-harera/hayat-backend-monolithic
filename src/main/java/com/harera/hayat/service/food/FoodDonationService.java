package com.harera.hayat.service.food;

import static com.harera.hayat.util.FileUtils.convertMultiPartToFile;
import static com.harera.hayat.util.ObjectMapperUtils.mapAll;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.DonationCategory;
import com.harera.hayat.model.DonationStatus;
import com.harera.hayat.model.ai.Prediction;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.food.FoodCategory;
import com.harera.hayat.model.food.FoodDonation;
import com.harera.hayat.model.food.FoodDonationRequest;
import com.harera.hayat.model.food.FoodDonationResponse;
import com.harera.hayat.model.food.FoodDonationUpdateRequest;
import com.harera.hayat.model.food.FoodUnit;
import com.harera.hayat.model.medicine.MedicineDonation;
import com.harera.hayat.model.user.User;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.food.FoodCategoryRepository;
import com.harera.hayat.repository.food.FoodDonationRepository;
import com.harera.hayat.repository.food.FoodUnitRepository;
import com.harera.hayat.service.DonationNotificationsService;
import com.harera.hayat.service.ai.PredictionService;
import com.harera.hayat.service.file.CloudFileService;
import com.harera.hayat.service.jwt.JwtService;

import jakarta.ws.rs.BadRequestException;

@Service
public class FoodDonationService {

    private final FoodDonationValidation foodDonationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final FoodUnitRepository foodUnitRepository;
    private final FoodDonationRepository foodDonationRepository;
    private final int foodDonationExpirationDays;
    private final FoodCategoryRepository foodCategoryRepository;
    private final CloudFileService cloudFileService;
    private final DonationNotificationsService donationNotificationsService;
    private final PredictionService predictionService;
    private final JwtService jwtUtils;
    private final UserRepository userRepository;

    public FoodDonationService(FoodDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper,
                    FoodUnitRepository foodUnitRepository,
                    FoodDonationRepository foodDonationRepository,
                    @Value("${donation.food.expiration_in_days}") int foodDonationExpirationDays,
                    FoodCategoryRepository foodCategoryRepository,
                    CloudFileService cloudFileService,
                    DonationNotificationsService donationNotificationsService,
                    PredictionService predictionService, JwtService jwtUtils,
                    UserRepository userRepository) {
        this.foodDonationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.foodUnitRepository = foodUnitRepository;
        this.foodDonationRepository = foodDonationRepository;
        this.foodDonationExpirationDays = foodDonationExpirationDays;
        this.foodCategoryRepository = foodCategoryRepository;
        this.cloudFileService = cloudFileService;
        this.donationNotificationsService = donationNotificationsService;
        this.predictionService = predictionService;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public FoodDonationResponse create(FoodDonationRequest foodDonationRequest,
                    String authorization) {
        foodDonationValidation.validateCreate(foodDonationRequest);

        FoodDonation foodDonation =
                        modelMapper.map(foodDonationRequest, FoodDonation.class);
        foodDonation.setStatus(DonationStatus.PENDING);
        foodDonation.setCategory(DonationCategory.FOOD);
        foodDonation.setDonationDate(OffsetDateTime.now());
        foodDonation.setDonationExpirationDate(getDonationExpirationDate());
        foodDonation.setCity(getCity(foodDonationRequest.getCityId()));
        foodDonation.setUser(getUser(authorization));
        foodDonation.setFoodUnit(getUnit(foodDonationRequest.getFoodUnitId()));

        foodDonationRepository.save(foodDonation);

        donationNotificationsService.notifyProcessingDonation(foodDonation);
        //        reviewDonation(foodDonation);

        return modelMapper.map(foodDonation, FoodDonationResponse.class);
    }

    private void reviewDonation(FoodDonation foodDonation) {
        Prediction prediction = predictionService.predict(
                        foodDonation.getTitle() + " " + foodDonation.getDescription());
        if (Objects.equals(prediction.getLabel(), "FOOD")
                        && prediction.getCertainty() > 0.5) {
            foodDonation.setStatus(DonationStatus.ACTIVE);
            donationNotificationsService.notifyDonationAccepted(foodDonation);
        } else {
            donationNotificationsService.notifyDonationRejected(foodDonation);
            foodDonation.setStatus(DonationStatus.REJECTED);
        }
        foodDonationRepository.save(foodDonation);
    }

    public FoodDonationResponse update(Long id, FoodDonationUpdateRequest request) {
        foodDonationValidation.validateUpdate(id, request);

        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));

        modelMapper.map(request, foodDonation);

        foodDonation.setCity(getCity(request.getCityId()));
        foodDonation.setFoodUnit(getUnit(request.getFoodUnitId()));
        foodDonation.setFoodCategory(getCategory(request.getFoodCategoryId()));

        foodDonationRepository.save(foodDonation);

        return modelMapper.map(foodDonation, FoodDonationResponse.class);
    }

    private OffsetDateTime getDonationExpirationDate() {
        return OffsetDateTime.now().plusDays(foodDonationExpirationDays);
    }

    private City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(
                        () -> new EntityNotFoundException(City.class, cityId));
    }

    private FoodUnit getUnit(Long id) {
        return foodUnitRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodUnit.class, id));
    }

    private FoodCategory getCategory(Long id) {
        if (id != null) {
            return foodCategoryRepository.findById(id).orElseThrow(
                            () -> new EntityNotFoundException(FoodCategory.class, id));
        }
        return null;
    }

    public List<FoodDonationResponse> list(int pageSize, int pageNumber) {
        List<FoodDonation> foodDonationList = foodDonationRepository
                        .findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        List<FoodDonationResponse> response = new LinkedList<>();
        for (FoodDonation foodDonation : foodDonationList) {
            FoodDonationResponse foodDonationResponse =
                            modelMapper.map(foodDonation, FoodDonationResponse.class);
            response.add(foodDonationResponse);
        }
        return response;
    }

    public FoodDonationResponse get(Long id) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));
        return modelMapper.map(foodDonation, FoodDonationResponse.class);
    }

    public FoodDonationResponse updateImage(Long id, MultipartFile file) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));

        String imageUrl = cloudFileService.uploadFile(convertMultiPartToFile(file));
        if (foodDonation.getImageUrl() == null) {
            foodDonation.setImageUrl(imageUrl);
        } else {
            cloudFileService.deleteFile(foodDonation.getImageUrl());
            foodDonation.setImageUrl(imageUrl);
        }

        foodDonationRepository.save(foodDonation);
        return modelMapper.map(foodDonation, FoodDonationResponse.class);
    }

    public void upvote(Long id, String authorization) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));
        foodDonation.upvote(getUser(authorization));
        foodDonationRepository.save(foodDonation);
    }

    private User getUser(String token) {
        long subject = Long.parseLong(jwtUtils.extractUserSubject(token));
        return userRepository.findById(subject).orElseThrow(
                        () -> new EntityNotFoundException(User.class, subject, ""));
    }

    public void downvote(Long id, String authorization) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodDonation.class, id));
        foodDonation.downvote(getUser(authorization));
        foodDonationRepository.save(foodDonation);
    }

    public List<FoodDonationResponse> search(String query, Integer page) {
        page = Integer.max(page, 1) - 1;
        List<FoodDonation> search = foodDonationRepository.search(query,
                        Pageable.ofSize(16).withPage(page));
        return mapAll(search, FoodDonationResponse.class);
    }

    public void receive(Long id) {
        FoodDonation foodDonation = foodDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(MedicineDonation.class, id));
        if (foodDonation.getStatus() != DonationStatus.ACTIVE) {
            throw new BadRequestException("Donation is not active");
        }
        foodDonation.setStatus(DonationStatus.DONE);
        foodDonation.getUser().setReputation(foodDonation.getUser().getReputation() + 50);
        foodDonationRepository.save(foodDonation);
    }
}

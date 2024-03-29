package com.harera.hayat.service.clothing;

import static com.harera.hayat.util.FileUtils.convertMultiPartToFile;
import static com.harera.hayat.util.ObjectMapperUtils.mapAll;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.DonationCategory;
import com.harera.hayat.model.DonationStatus;
import com.harera.hayat.model.ai.Prediction;
import com.harera.hayat.model.clothing.ClothingDonation;
import com.harera.hayat.model.clothing.ClothingDonationRequest;
import com.harera.hayat.model.clothing.ClothingDonationResponse;
import com.harera.hayat.model.clothing.ClothingDonationUpdateRequest;
import com.harera.hayat.model.medicine.MedicineDonation;
import com.harera.hayat.repository.clothing.ClothingDonationRepository;
import com.harera.hayat.service.DonationNotificationsService;
import com.harera.hayat.service.ai.PredictionService;
import com.harera.hayat.service.city.CityService;
import com.harera.hayat.service.file.CloudFileService;
import com.harera.hayat.service.jwt.JwtService;

import jakarta.ws.rs.BadRequestException;

@Service
public class ClothingDonationService {

    private final ClothingDonationValidation clothingDonationValidation;
    private final CityService citService;
    private final ModelMapper modelMapper;
    private final ClothingDonationRepository clothingDonationRepository;
    private final CloudFileService cloudFileService;
    private final ClothingCategoryService clothingCategoryService;
    private final ClothingSizeService clothingSizeService;
    private final ClothingTypeService clothingTypeService;
    private final ClothingConditionService clothingConditionService;
    private final ClothingSeasonService clothingSeasonService;
    private final DonationNotificationsService donationNotificationsService;
    private final PredictionService predictionService;
    private final JwtService jwtService;
    @Value("${donation.clothing.expiration_in_days:45}")
    private int expirationDays;

    public ClothingDonationService(ClothingDonationValidation donationValidation,
                    ModelMapper modelMapper, CityService citService,
                    ClothingDonationRepository clothingDonationRepository,
                    CloudFileService cloudFileService,
                    ClothingCategoryService clothingCategoryService,
                    ClothingSizeService clothingSizeService,
                    ClothingTypeService clothingTypeService,
                    ClothingConditionService clothingConditionService,
                    ClothingSeasonService clothingSeasonService,
                    DonationNotificationsService donationNotificationsService,
                    PredictionService predictionService, JwtService jwtService) {
        this.clothingDonationValidation = donationValidation;
        this.modelMapper = modelMapper;
        this.citService = citService;
        this.clothingDonationRepository = clothingDonationRepository;
        this.cloudFileService = cloudFileService;
        this.clothingCategoryService = clothingCategoryService;
        this.clothingSizeService = clothingSizeService;
        this.clothingTypeService = clothingTypeService;
        this.clothingConditionService = clothingConditionService;
        this.clothingSeasonService = clothingSeasonService;
        this.donationNotificationsService = donationNotificationsService;
        this.predictionService = predictionService;
        this.jwtService = jwtService;
    }

    public ClothingDonationResponse create(
                    ClothingDonationRequest clothingDonationRequest,
                    String authorization) {
        clothingDonationValidation.validateCreate(clothingDonationRequest);

        ClothingDonation clothingDonation =
                        modelMapper.map(clothingDonationRequest, ClothingDonation.class);

        clothingDonation.setCategory(DonationCategory.CLOTHING);
        clothingDonation.setStatus(DonationStatus.PENDING);
        clothingDonation.setDonationDate(OffsetDateTime.now());
        clothingDonation.setDonationExpirationDate(
                        OffsetDateTime.now().plusDays(expirationDays));
        clothingDonation.setUser(jwtService.getUser(authorization));
        clothingDonation.setCity(citService.getCity(clothingDonationRequest.getCityId()));
        assignClothingData(clothingDonation, clothingDonationRequest);

        clothingDonationRepository.save(clothingDonation);

        donationNotificationsService.notifyProcessingDonation(clothingDonation);
//        reviewDonation(clothingDonation);

        return modelMapper.map(clothingDonation, ClothingDonationResponse.class);
    }

    private void reviewDonation(ClothingDonation bookDonation) {
        Prediction prediction = predictionService.predict(
                        bookDonation.getTitle() + " " + bookDonation.getDescription());
        if (Objects.equals(prediction.getLabel(), "CLOTHING")
                        && prediction.getCertainty() > 0.5) {
            bookDonation.setStatus(DonationStatus.ACTIVE);
            donationNotificationsService.notifyDonationAccepted(bookDonation);
        } else {
            donationNotificationsService.notifyDonationRejected(bookDonation);
            bookDonation.setStatus(DonationStatus.REJECTED);
        }
        clothingDonationRepository.save(bookDonation);
    }

    private void assignClothingData(ClothingDonation clothingDonation,
                    ClothingDonationRequest clothingDonationRequest) {
        if (clothingDonationRequest.getClothingCategoryId() != null) {
            clothingDonation.setClothingCategory(clothingCategoryService
                            .get(clothingDonationRequest.getClothingCategoryId()));
        }

        if (clothingDonationRequest.getClothingSizeId() != null) {
            clothingDonation.setClothingSize(clothingSizeService
                            .get(clothingDonationRequest.getClothingSizeId()));
        }

        if (clothingDonationRequest.getClothingTypeId() != null) {
            clothingDonation.setClothingType(clothingTypeService
                            .get(clothingDonationRequest.getClothingTypeId()));
        }

        if (clothingDonationRequest.getClothingConditionId() != null) {
            clothingDonation.setClothingCondition(clothingConditionService
                            .get(clothingDonationRequest.getClothingConditionId()));
        }

        if (clothingDonationRequest.getClothingSeasonId() != null) {
            clothingDonation.setClothingSeason(clothingSeasonService
                            .get(clothingDonationRequest.getClothingSeasonId()));
        }
    }

    public ClothingDonationResponse update(Long id,
                    ClothingDonationUpdateRequest request) {
        clothingDonationValidation.validateUpdate(id, request);

        ClothingDonation clothingDonation =
                        clothingDonationRepository.findById(id).orElseThrow();
        modelMapper.map(request, clothingDonation);

        // TODO: set user
        clothingDonation.setCity(citService.getCity(request.getCityId()));

        clothingDonationRepository.save(clothingDonation);
        return modelMapper.map(clothingDonation, ClothingDonationResponse.class);
    }

    public List<ClothingDonationResponse> list(int page) {
        page = Math.max(page, 1) - 1;
        List<ClothingDonation> clothingDonationList = clothingDonationRepository
                        .findAll(PageRequest.of(page, 16)).getContent();
        List<ClothingDonationResponse> response = new LinkedList<>();
        for (ClothingDonation clothingDonation : clothingDonationList) {
            ClothingDonationResponse clothingDonationResponse = modelMapper
                            .map(clothingDonation, ClothingDonationResponse.class);
            response.add(clothingDonationResponse);
        }
        return response;
    }

    public ClothingDonationResponse get(Long id) {
        ClothingDonation clothingDonation = clothingDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        ClothingDonation.class, id));
        return modelMapper.map(clothingDonation, ClothingDonationResponse.class);
    }

    public ClothingDonationResponse updateImage(Long id, MultipartFile file) {
        ClothingDonation clothingDonation = clothingDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        ClothingDonation.class, id));

        String imageUrl = cloudFileService.uploadFile(convertMultiPartToFile(file));
        String oldImageUrl = clothingDonation.getImageUrl();
        clothingDonation.setImageUrl(imageUrl);

        if (oldImageUrl != null) {
            cloudFileService.deleteFile(oldImageUrl);
        }

        clothingDonationRepository.save(clothingDonation);
        return modelMapper.map(clothingDonation, ClothingDonationResponse.class);
    }

    public List<ClothingDonationResponse> search(String query, int page) {
        page = Math.max(page, 1) - 1;
        List<ClothingDonation> clothingDonationList = clothingDonationRepository
                        .search(query, PageRequest.of(page, 16));
        return mapAll(clothingDonationList, ClothingDonationResponse.class);
    }

    public void upvote(Long id, String authorization) {
        ClothingDonation clothingDonation = clothingDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        ClothingDonation.class, id));
        clothingDonation.upvote(jwtService.getUser(authorization));
        clothingDonationRepository.save(clothingDonation);
    }

    public void downvote(Long id, String authorization) {
        ClothingDonation clothingDonation = clothingDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        ClothingDonation.class, id));
        clothingDonation.downvote(jwtService.getUser(authorization));
        clothingDonationRepository.save(clothingDonation);
    }

    public void receive(Long id) {
        ClothingDonation clothingDonation = clothingDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        MedicineDonation.class, id));
        if (clothingDonation.getStatus() != DonationStatus.ACTIVE) {
            throw new BadRequestException("Donation is not active");
        }
        clothingDonation.setStatus(DonationStatus.DONE);
        clothingDonation.getUser()
                        .setReputation(clothingDonation.getUser().getReputation() + 50);
        clothingDonationRepository.save(clothingDonation);
    }
}

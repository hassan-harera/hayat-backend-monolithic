package com.harera.hayat.service.medicine;

import static com.harera.hayat.util.FileUtils.convertMultiPartToFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.exception.DocumentNotFoundException;
import com.harera.hayat.model.NeedCategory;
import com.harera.hayat.model.NeedStatus;
import com.harera.hayat.model.ai.Prediction;
import com.harera.hayat.model.medicine.MedicineNeed;
import com.harera.hayat.model.medicine.MedicineNeedRequest;
import com.harera.hayat.model.medicine.MedicineNeedResponse;
import com.harera.hayat.model.medicine.MedicineNeedUpdateRequest;
import com.harera.hayat.model.user.UserDto;
import com.harera.hayat.repository.medicine.MedicineNeedRepository;
import com.harera.hayat.service.BaseService;
import com.harera.hayat.service.NeedNotificationsService;
import com.harera.hayat.service.ai.PredictionService;
import com.harera.hayat.service.city.CityService;
import com.harera.hayat.service.file.CloudFileService;
import com.harera.hayat.service.user.UserService;
import com.harera.hayat.util.ObjectMapperUtils;

@Service
public class MedicineNeedService implements BaseService {

    private final MedicineNeedValidation needValidation;
    private final CityService cityService;
    private final MedicineUnitService medicineUnitService;
    private final MedicineService medicineService;
    private final ModelMapper modelMapper;
    private final MedicineNeedRepository medicineNeedRepository;
    private final UserService userService;
    private final CloudFileService cloudFileService;
    private final NeedNotificationsService needNotificationsService;
    private final PredictionService predictionService;

    public MedicineNeedService(MedicineNeedValidation needValidation,
                    CityService cityService, MedicineUnitService medicineUnitService,
                    MedicineService medicineService, ModelMapper modelMapper,
                    MedicineNeedRepository medicineNeedRepository,
                    UserService userService, CloudFileService cloudFileService,
                    NeedNotificationsService needNotificationsService,
                    PredictionService predictionService) {
        this.needValidation = needValidation;
        this.cityService = cityService;
        this.medicineUnitService = medicineUnitService;
        this.medicineService = medicineService;
        this.modelMapper = modelMapper;
        this.medicineNeedRepository = medicineNeedRepository;
        this.userService = userService;
        this.cloudFileService = cloudFileService;
        this.needNotificationsService = needNotificationsService;
        this.predictionService = predictionService;
    }

    public MedicineNeedResponse create(MedicineNeedRequest medicineNeedRequest,
                    String authorization) {
        needValidation.validateCreate(medicineNeedRequest);

        MedicineNeed medicineNeed =
                        modelMapper.map(medicineNeedRequest, MedicineNeed.class);
        medicineNeed.setCategory(NeedCategory.MEDICINE);
        medicineNeed.setStatus(NeedStatus.PENDING);
        medicineNeed.setCity(cityService.get(medicineNeedRequest.getCityId()));
        medicineNeed.setUser(modelMapper.map(userService.getUser(authorization),
                        UserDto.class));
        medicineNeed.setMedicineUnit(
                        medicineUnitService.get(medicineNeedRequest.getMedicineUnitId()));
        medicineNeed.setMedicine(
                        medicineService.get(medicineNeedRequest.getMedicineId()));
        medicineNeed.setNeedDate(LocalDateTime.now());
        medicineNeed.setNeedExpirationDate(LocalDateTime.now().plusDays(45));

        medicineNeedRepository.save(medicineNeed);

        needNotificationsService.notifyProcessingNeed(medicineNeed);
//        reviewNeed(medicineNeed);

        return modelMapper.map(medicineNeed, MedicineNeedResponse.class);
    }

    private void reviewNeed(MedicineNeed medicineNeed) {
        Prediction prediction = predictionService.predict(
                        medicineNeed.getTitle() + " " + medicineNeed.getDescription());
        if (Objects.equals(prediction.getLabel(), "MEDICINE")
                        && prediction.getCertainty() > 0.5) {
            medicineNeed.setStatus(NeedStatus.ACTIVE);
            needNotificationsService.notifyDonationAccepted(medicineNeed);
        } else {
            needNotificationsService.notifyDonationRejected(medicineNeed);
            medicineNeed.setStatus(NeedStatus.REJECTED);
        }
        medicineNeedRepository.save(medicineNeed);
    }

    public MedicineNeedResponse get(String id) {
        return modelMapper.map(
                        medicineNeedRepository.findById(id)
                                        .orElseThrow(() -> new DocumentNotFoundException(
                                                        MedicineNeed.class, id)),
                        MedicineNeedResponse.class);
    }

    public List<MedicineNeedResponse> list(int page) {
        page = Integer.max(page, 1) - 1;
        return medicineNeedRepository.findAll(Pageable.ofSize(16).withPage(page))
                        .map(medicineNeed -> modelMapper.map(medicineNeed,
                                        MedicineNeedResponse.class))
                        .toList();
    }

    public MedicineNeedResponse update(String id, MedicineNeedUpdateRequest request) {
        needValidation.validateUpdate(id, request);

        MedicineNeed medicineNeed = medicineNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException(MedicineNeed.class, id));
        modelMapper.map(request, medicineNeed);

        medicineNeed.setCategory(NeedCategory.MEDICINE);
        medicineNeed.setStatus(NeedStatus.PENDING);
        medicineNeed.setCity(cityService.get(request.getCityId()));
        medicineNeed.setMedicineUnit(
                        medicineUnitService.get(request.getMedicineUnitId()));
        medicineNeed.setMedicine(medicineService.get(request.getMedicineId()));

        // TODO: 28/02/23 send request to ai model

        medicineNeedRepository.save(medicineNeed);
        return modelMapper.map(medicineNeed, MedicineNeedResponse.class);

    }

    public List<MedicineNeedResponse> search(String query, int page) {
        return search(query, page, 16);
    }

    public MedicineNeedResponse updateImage(String id, MultipartFile file) {
        MedicineNeed medicineNeed = medicineNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException(MedicineNeed.class, id));

        String imageUrl = cloudFileService.uploadFile(convertMultiPartToFile(file));
        if (medicineNeed.getImageUrl() == null) {
            medicineNeed.setImageUrl(imageUrl);
        } else {
            cloudFileService.deleteFile(medicineNeed.getImageUrl());
            medicineNeed.setImageUrl(imageUrl);
        }

        medicineNeedRepository.save(medicineNeed);
        return modelMapper.map(medicineNeed, MedicineNeedResponse.class);
    }

    public void upvote(String id, String authorization) {
        MedicineNeed medicineNeed = medicineNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException(MedicineNeed.class, id));
        medicineNeed.upvote(userService.getUser(authorization).getId());
        medicineNeedRepository.save(medicineNeed);
    }

    public void downvote(String id, String authorization) {
        MedicineNeed medicineNeed = medicineNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException(MedicineNeed.class, id));
        medicineNeed.downvote(userService.getUser(authorization).getId());
        medicineNeedRepository.save(medicineNeed);
    }

    public List<MedicineNeedResponse> search(String query, int page, int pageSize) {
        page = Integer.max(page, 1) - 1;
        List<MedicineNeed> medicineNeeds = medicineNeedRepository.search(query,
                        NeedStatus.ACTIVE, PageRequest.of(page, pageSize));
        return ObjectMapperUtils.mapAll(medicineNeeds, MedicineNeedResponse.class);
    }
}

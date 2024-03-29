package com.harera.hayat.service.possession;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.DocumentNotFoundException;
import com.harera.hayat.model.NeedCategory;
import com.harera.hayat.model.NeedStatus;
import com.harera.hayat.model.possession.PossessionNeed;
import com.harera.hayat.model.possession.PossessionNeedRequest;
import com.harera.hayat.model.possession.PossessionNeedResponse;
import com.harera.hayat.model.user.UserDto;
import com.harera.hayat.repository.possession.PossessionCategoryRepository;
import com.harera.hayat.repository.possession.PossessionConditionRepository;
import com.harera.hayat.repository.possession.PossessionNeedRepository;
import com.harera.hayat.service.BaseService;
import com.harera.hayat.service.NeedNotificationsService;
import com.harera.hayat.service.city.CityService;
import com.harera.hayat.service.user.UserService;

@Service
public class PossessionNeedService implements BaseService {

    private final PossessionValidation possessionValidation;
    private final ModelMapper modelMapper;
    private final CityService cityService;
    private final int possessionNeedExpirationDays = 45;
    private final PossessionNeedRepository possessionNeedRepository;
    private final PossessionCategoryRepository possessionCategoryRepository;
    private final PossessionConditionRepository possessionConditionRepository;
    private final UserService userService;
    private final NeedNotificationsService needNotificationsService;

    public PossessionNeedService(PossessionValidation possessionValidation,
                    ModelMapper modelMapper, CityService cityService,
                    PossessionNeedRepository possessionNeedRepository,
                    PossessionCategoryRepository possessionCategoryRepository,
                    PossessionConditionRepository possessionConditionRepository,
                    UserService userService,
                    NeedNotificationsService needNotificationsService) {
        this.possessionValidation = possessionValidation;
        this.modelMapper = modelMapper;
        this.cityService = cityService;
        this.possessionNeedRepository = possessionNeedRepository;
        this.possessionCategoryRepository = possessionCategoryRepository;
        this.possessionConditionRepository = possessionConditionRepository;
        this.userService = userService;
        this.needNotificationsService = needNotificationsService;
    }

    public PossessionNeedResponse create(PossessionNeedRequest possessionNeedRequest,
                    String authorization) {
        possessionValidation.validateCreate(possessionNeedRequest);

        PossessionNeed possessionNeed =
                        modelMapper.map(possessionNeedRequest, PossessionNeed.class);
        possessionNeed.setNeedDate(LocalDateTime.now());
        possessionNeed.setNeedExpirationDate(
                        LocalDateTime.now().plusDays(possessionNeedExpirationDays));
        possessionNeed.setCategory(NeedCategory.POSSESSION);
        possessionNeed.setUser(modelMapper.map(userService.getUser(authorization),
                        UserDto.class));
        possessionNeed.setCity(cityService.get(possessionNeedRequest.getCityId()));
        possessionNeed.setPossessionCategory(possessionCategoryRepository
                        .findById(possessionNeedRequest.getPossessionCategoryId()).get());
        possessionNeed.setPossessionCondition(possessionConditionRepository
                        .findById(possessionNeedRequest.getPossessionConditionId())
                        .get());
        possessionNeed.setStatus(NeedStatus.PENDING);

        needNotificationsService.notifyProcessingNeed(possessionNeed);

        possessionNeed = possessionNeedRepository.save(possessionNeed);
        // TODO: send request to ml service
        return modelMapper.map(possessionNeedRepository.save(possessionNeed),
                        PossessionNeedResponse.class);
    }

    public void upvote(String id, String authorization) {
        PossessionNeed possessionNeed = possessionNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException("Need not found"));
        possessionNeed.upvote(userService.getUser(authorization).getId());
        possessionNeedRepository.save(possessionNeed);
    }

    public void downvote(String id, String authorization) {
        PossessionNeed possessionNeed = possessionNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException("Need not found"));
        possessionNeed.downvote(userService.getUser(authorization).getId());
        possessionNeedRepository.save(possessionNeed);
    }
}

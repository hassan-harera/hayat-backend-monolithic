package com.harera.hayat.service.possession;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.possession.PossessionCategory;
import com.harera.hayat.model.possession.PossessionCondition;
import com.harera.hayat.model.possession.PossessionNeedRequest;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.possession.PossessionCategoryRepository;
import com.harera.hayat.repository.possession.PossessionConditionRepository;
import com.harera.hayat.service.NeedValidation;
import com.harera.hayat.util.ErrorCode;

@Service
public class PossessionValidation extends NeedValidation {

    private final PossessionCategoryRepository possessionCategoryRepository;
    private final PossessionConditionRepository possessionConditionRepository;

    public PossessionValidation(CityRepository cityRepository,
                    PossessionCategoryRepository possessionCategoryRepository,
                    PossessionConditionRepository possessionConditionRepository) {
        super(cityRepository);
        this.possessionCategoryRepository = possessionCategoryRepository;
        this.possessionConditionRepository = possessionConditionRepository;
    }

    public void validateCreate(PossessionNeedRequest possessionNeedRequest) {
        super.validate(possessionNeedRequest);
        validateMandatoryCreate(possessionNeedRequest);
        validateExistingCreate(possessionNeedRequest);
    }

    private void validateExistingCreate(PossessionNeedRequest possessionNeedRequest) {
        if (!possessionCategoryRepository
                        .existsById(possessionNeedRequest.getPossessionCategoryId()))
            throw new EntityNotFoundException(PossessionCategory.class,
                            possessionNeedRequest.getPossessionCategoryId());
        if (!possessionConditionRepository
                        .existsById(possessionNeedRequest.getPossessionConditionId()))
            throw new EntityNotFoundException(PossessionCondition.class,
                            possessionNeedRequest.getPossessionCategoryId());
    }

    private void validateMandatoryCreate(PossessionNeedRequest possessionNeedRequest) {
        if (possessionNeedRequest.getPossessionCategoryId() == null)
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_POSSESSION_NEED_CATEGORY_ID,
                            "category_id");
        if (possessionNeedRequest.getPossessionConditionId() == null)
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_POSSESSION_NEED_CONDITION_ID,
                            "condition_id");
    }
}

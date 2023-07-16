package com.harera.hayat.service;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.model.NeedDto;
import com.harera.hayat.model.blood.BloodNeedUpdateRequest;
import com.harera.hayat.model.medicine.MedicineNeedRequest;
import com.harera.hayat.model.medicine.MedicineNeedUpdateRequest;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.util.ErrorCode;
import com.harera.hayat.util.FieldFormat;

@Service
public class NeedValidation {

    private final CityRepository cityRepository;

    public NeedValidation(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void validate(NeedDto needDto) {
        validateMandatory(needDto);
        validateFormat(needDto);
    }

    private void validateFormat(NeedDto needDto) {
        if (needDto.getTitle().length() < 4 || needDto.getTitle().length() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_NEED_TITLE, "title",
                            FieldFormat.TITLE_PATTERN);
        }
    }

    private void validateMandatory(NeedDto needDto) {
        if (isBlank(needDto.getTitle())) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_NEED_TITLE, "title");
        }

        if (needDto.getCommunicationMethod() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_NEED_COMMUNICATION_METHOD,
                            "communication_method");
        }

        if (needDto.getCityId() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_NEED_CITY_ID,
                            "city_id");
        }
    }

    public void validateCreate(MedicineNeedRequest medicineNeedRequest) {
        validateMandatory(medicineNeedRequest);
        validateFormat(medicineNeedRequest);
    }

    public void validateUpdate(MedicineNeedUpdateRequest medicineNeedRequest) {
        validateMandatory(medicineNeedRequest);
        validateFormat(medicineNeedRequest);
    }

    public void validateUpdate(BloodNeedUpdateRequest request) {

    }
}

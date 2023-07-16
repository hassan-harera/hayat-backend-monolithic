package com.harera.hayat.service.food;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.util.ErrorCode;
import com.harera.hayat.model.food.FoodDonation;
import com.harera.hayat.model.food.FoodDonationDto;
import com.harera.hayat.model.food.FoodDonationUpdateRequest;
import com.harera.hayat.repository.food.FoodDonationRepository;
import com.harera.hayat.service.DonationValidation;

@Service
public class FoodDonationValidation {

    private final DonationValidation donationValidation;
    private final FoodDonationRepository foodDonationRepository;

    public FoodDonationValidation(DonationValidation donationValidation,
                    FoodDonationRepository foodDonationRepository) {
        this.donationValidation = donationValidation;
        this.foodDonationRepository = foodDonationRepository;
    }

    public void validateCreate(FoodDonationDto foodDonationRequest) {
        donationValidation.validateCreate(foodDonationRequest);
        validateMandatory(foodDonationRequest);
        validateFormat(foodDonationRequest);
    }

    private void validateFormat(FoodDonationDto foodDonationRequest) {
        if (foodDonationRequest.getQuantity() < 0
                        || foodDonationRequest.getQuantity() > 10000) {
            throw new FieldFormatException(ErrorCode.FORMAT_FOOD_DONATION_AMOUNT,
                            "quantity", foodDonationRequest.getQuantity().toString());
        }
    }

    private void validateMandatory(FoodDonationDto foodDonationRequest) {
        if (foodDonationRequest.getQuantity() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_FOOD_DONATION_AMOUNT,
                            "quantity");
        }
        if (foodDonationRequest.getFoodUnitId() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_FOOD_DONATION_UNIT,
                            "unit");
        }
        if (foodDonationRequest.getFoodExpirationDate() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_FOOD_DONATION_FOOD_EXPIRATION_DATE,
                            "donation_expiration_date");
        }
    }

    public void validateUpdate(Long id, FoodDonationUpdateRequest request) {
        donationValidation.validateUpdate(request);
        validateMandatory(request);
        validateFormat(request);
        validateUpdateExisting(id, request);
    }

    private void validateUpdateExisting(Long id, FoodDonationUpdateRequest request)
                    throws EntityNotFoundException {
        if (!foodDonationRepository.existsById(id)) {
            throw new EntityNotFoundException(FoodDonation.class, id,
                            ErrorCode.NOT_FOUND_FOOD_DONATION);
        }
    }

    private void validateExisting(FoodDonationUpdateRequest request)
                    throws EntityNotFoundException {
    }
}

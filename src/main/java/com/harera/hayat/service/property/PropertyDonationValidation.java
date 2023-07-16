package com.harera.hayat.service.property;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.util.ErrorCode;
import com.harera.hayat.model.property.PropertyDonation;
import com.harera.hayat.model.property.PropertyDonationDto;
import com.harera.hayat.model.property.PropertyDonationRequest;
import com.harera.hayat.model.property.PropertyDonationUpdateRequest;
import com.harera.hayat.repository.property.PropertyDonationRepository;
import com.harera.hayat.service.DonationValidation;

@Service
public class PropertyDonationValidation {

    private final DonationValidation donationValidation;
    private final PropertyDonationRepository propertyDonationRepository;

    public PropertyDonationValidation(DonationValidation donationValidation,
                    PropertyDonationRepository propertyDonationRepository) {
        this.donationValidation = donationValidation;
        this.propertyDonationRepository = propertyDonationRepository;
    }

    public void validateCreate(PropertyDonationRequest propertyDonationRequest) {
        donationValidation.validateCreate(propertyDonationRequest);
        validateMandatory(propertyDonationRequest);
        validateFormat(propertyDonationRequest);
    }

    private void validateMandatory(PropertyDonationDto propertyDonationRequest) {
        if (propertyDonationRequest.getRooms() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_PROPERTY_DONATION_ROOMS,
                            "rooms");
        }
        if (propertyDonationRequest.getBathrooms() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_PROPERTY_DONATION_BATH_ROOMS,
                            "bathrooms");
        }
        if (propertyDonationRequest.getKitchens() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_PROPERTY_DONATION_KITCHENS, "kitchens");
        }
        if (propertyDonationRequest.getPeopleCapacity() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_PROPERTY_DONATION_PEOPLE_CAPACITY,
                            "people_capacity");
        }
        if (propertyDonationRequest.getAvailableFrom() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_PROPERTY_DONATION_AVAILABLE_FROM,
                            "available_from");
        }
        if (propertyDonationRequest.getAvailableTo() == null) {
            throw new MandatoryFieldException(
                            ErrorCode.MANDATORY_PROPERTY_DONATION_AVAILABLE_TO,
                            "available_to");
        }
    }

    private void validateFormat(PropertyDonationDto propertyDonationRequest) {
        if (propertyDonationRequest.getRooms() < 1
                        || propertyDonationRequest.getRooms() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_PROPERTY_DONATION_ROOMS,
                            "rooms");
        }

        if (propertyDonationRequest.getBathrooms() < 1
                        || propertyDonationRequest.getBathrooms() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_PROPERTY_DONATION_BATH_ROOMS,
                            "bathrooms");
        }

        if (propertyDonationRequest.getKitchens() < 1
                        || propertyDonationRequest.getKitchens() > 100) {
            throw new FieldFormatException(ErrorCode.FORMAT_PROPERTY_DONATION_KITCHENS,
                            "kitchens");
        }

        if (propertyDonationRequest.getPeopleCapacity() < 1
                        || propertyDonationRequest.getPeopleCapacity() > 1000) {
            throw new FieldFormatException(
                            ErrorCode.FORMAT_PROPERTY_DONATION_PEOPLE_CAPACITY,
                            "people_capacity");
        }

        if (propertyDonationRequest.getAvailableFrom().isBefore(OffsetDateTime.now())) {
            throw new FieldFormatException(
                            ErrorCode.FORMAT_PROPERTY_DONATION_AVAILABLE_FROM,
                            "available_from");
        }

        if (propertyDonationRequest.getAvailableTo()
                        .isBefore(OffsetDateTime.now().plusDays(1))) {
            throw new FieldFormatException(
                            ErrorCode.FORMAT_PROPERTY_DONATION_AVAILABLE_TO,
                            "available_to");
        }
    }

    public void validateUpdate(Long id,
                    PropertyDonationUpdateRequest propertyDonationUpdateRequest) {
        donationValidation.validateUpdate(propertyDonationUpdateRequest);
        validateMandatory(propertyDonationUpdateRequest);
        validateFormat(propertyDonationUpdateRequest);
        validateExisting(id);
    }

    private void validateExisting(Long id) {
        if (!propertyDonationRepository.existsById(id)) {
            throw new EntityNotFoundException(PropertyDonation.class, id,
                            ErrorCode.NOT_FOUND_PROPERTY_DONATION);
        }
    }
}

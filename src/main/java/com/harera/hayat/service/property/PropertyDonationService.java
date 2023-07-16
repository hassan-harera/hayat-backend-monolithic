package com.harera.hayat.service.property;

import java.time.OffsetDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.util.ErrorCode;
import com.harera.hayat.model.DonationCategory;
import com.harera.hayat.model.DonationStatus;
import com.harera.hayat.model.city.City;
import com.harera.hayat.model.property.PropertyDonation;
import com.harera.hayat.model.property.PropertyDonationRequest;
import com.harera.hayat.model.property.PropertyDonationResponse;
import com.harera.hayat.model.property.PropertyDonationUpdateRequest;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.repository.property.PropertyDonationRepository;
import com.harera.hayat.service.DonationNotificationsService;
import com.harera.hayat.service.jwt.JwtService;

@Service
public class PropertyDonationService {

    private final int pageSize = 16;
    private final int expirationDays = 45;

    private final PropertyDonationRepository propertyDonationRepository;
    private final PropertyDonationValidation donationValidation;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final DonationNotificationsService donationNotificationsService;
    private final JwtService jwtService;

    public PropertyDonationService(PropertyDonationRepository propertyDonationRepository,
                    PropertyDonationValidation donationValidation,
                    CityRepository cityRepository, ModelMapper modelMapper,
                    DonationNotificationsService donationNotificationsService,
                    JwtService jwtService) {
        this.propertyDonationRepository = propertyDonationRepository;
        this.donationValidation = donationValidation;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.donationNotificationsService = donationNotificationsService;
        this.jwtService = jwtService;
    }

    public PropertyDonationResponse create(
                    PropertyDonationRequest propertyDonationRequest, String bearerToken) {
        donationValidation.validateCreate(propertyDonationRequest);
        PropertyDonation propertyDonation =
                        modelMapper.map(propertyDonationRequest, PropertyDonation.class);

        propertyDonation.setStatus(DonationStatus.PENDING);
        propertyDonation.setCategory(DonationCategory.PROPERTY);
        propertyDonation.setDonationDate(OffsetDateTime.now());
        propertyDonation.setDonationExpirationDate(
                        OffsetDateTime.now().plusDays(expirationDays));

        assignCity(propertyDonation, propertyDonationRequest.getCityId());
        propertyDonation.setUser(jwtService.getUser(bearerToken));
        donationNotificationsService.notifyProcessingDonation(propertyDonation);

        propertyDonationRepository.save(propertyDonation);
        return modelMapper.map(propertyDonation, PropertyDonationResponse.class);
    }

    private void assignCity(PropertyDonation donation, Long cityId) {
        City city = cityRepository.findById(cityId)
                        .orElseThrow(() -> new EntityNotFoundException(City.class, cityId,
                                        ErrorCode.NOT_FOUND_DONATION_CITY));
        donation.setCity(city);
    }

    public PropertyDonationResponse get(Long propertyDonationId) {
        PropertyDonation propertyDonation = propertyDonationRepository
                        .findById(propertyDonationId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        PropertyDonation.class, propertyDonationId,
                                        ErrorCode.NOT_FOUND_PROPERTY_DONATION));
        return modelMapper.map(propertyDonation, PropertyDonationResponse.class);
    }

    public PropertyDonationResponse update(Long id,
                    PropertyDonationUpdateRequest propertyDonationUpdateRequest) {
        donationValidation.validateUpdate(id, propertyDonationUpdateRequest);
        PropertyDonation propertyDonation =
                        propertyDonationRepository.findById(id).orElseThrow();

        modelMapper.map(propertyDonationUpdateRequest, propertyDonation);
        propertyDonationRepository.save(propertyDonation);
        return modelMapper.map(propertyDonation, PropertyDonationResponse.class);
    }

    public List<PropertyDonationResponse> list(int page) {
        return propertyDonationRepository
                        .findAll(Pageable.ofSize(pageSize).withPage(page))
                        .map(propertyDonation -> modelMapper.map(propertyDonation,
                                        PropertyDonationResponse.class))
                        .toList();
    }

    public void upvote(Long id, String authorization) {
        PropertyDonation propertyDonation = propertyDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        PropertyDonation.class, id,
                                        ErrorCode.NOT_FOUND_PROPERTY_DONATION));
        propertyDonation.upvote(jwtService.getUser(authorization));
        propertyDonationRepository.save(propertyDonation);
    }

    public void downvote(Long id, String authorization) {
        PropertyDonation propertyDonation = propertyDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        PropertyDonation.class, id,
                                        ErrorCode.NOT_FOUND_PROPERTY_DONATION));
        propertyDonation.downvote(jwtService.getUser(authorization));
        propertyDonationRepository.save(propertyDonation);
    }
}

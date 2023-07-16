package com.harera.hayat.model.clothing;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.BaseEntityDto;
import com.harera.hayat.model.CommunicationMethod;
import com.harera.hayat.model.DonationCategory;
import com.harera.hayat.model.DonationStatus;
import com.harera.hayat.model.city.CityDto;
import com.harera.hayat.model.user.UserDto;

import lombok.Data;

@Data
public class ClothingDonationResponse extends BaseEntityDto {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "donation_date")
    private OffsetDateTime donationDate;

    @JsonProperty(value = "donation_expiration_date")
    private OffsetDateTime donationExpirationDate;

    @JsonProperty(value = "category")
    private DonationCategory category;

    @JsonProperty(value = "status")
    private DonationStatus status;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    @JsonProperty(value = "city")
    private CityDto city;

    @JsonProperty(value = "user")
    private UserDto user;

    @JsonProperty(value = "image_url")
    private String imageUrl;

    @JsonProperty(value = "telegram_link")
    private String telegramLink;

    @JsonProperty(value = "whatsapp_link")
    private String whatsappLink;

    @JsonProperty(value = "qr_code")
    private String qrCode;

    @JsonProperty(value = "reputation")
    private Integer reputation;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("clothing_season")
    private ClothingSeason clothingSeason;

    @JsonProperty("clothing_condition")
    private ClothingCondition clothingCondition;

    @JsonProperty("clothing_size")
    private ClothingSize clothingSize;

    @JsonProperty("clothing_category")
    private ClothingType clothingType;

    @JsonProperty("clothing_type")
    private ClothingType type;
}

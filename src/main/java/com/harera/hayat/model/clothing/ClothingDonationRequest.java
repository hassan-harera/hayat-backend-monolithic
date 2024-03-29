package com.harera.hayat.model.clothing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.CommunicationMethod;

import lombok.Data;

@Data
public class ClothingDonationRequest {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    @JsonProperty("city_id")
    private Long cityId;

    @JsonProperty(value = "telegram_link")
    private String telegramLink;

    @JsonProperty(value = "whatsapp_link")
    private String whatsappLink;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("clothing_season_id")
    private Long clothingSeasonId;

    @JsonProperty("clothing_condition_id")
    private Long clothingConditionId;

    @JsonProperty("clothing_size_id")
    private Long clothingSizeId;

    @JsonProperty("clothing_category_id")
    private Long clothingCategoryId;

    @JsonProperty("clothing_type_id")
    private Long clothingTypeId;
}

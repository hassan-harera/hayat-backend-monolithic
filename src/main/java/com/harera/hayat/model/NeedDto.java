package com.harera.hayat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.city.CityDto;
import com.harera.hayat.model.user.UserDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NeedDto extends BaseDocument {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "need_date")
    private String needDate;

    @JsonProperty(value = "need_expiration_date")
    private String needExpirationDate;

    @JsonProperty(value = "category")
    private NeedCategory category;

    @JsonProperty(value = "status")
    private NeedStatus status;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    @JsonProperty(value = "image_url")
    private String imageUrl;

    @JsonProperty("city")
    private CityDto city;

    @JsonProperty("city_id")
    private Long cityId;

    @JsonProperty("user")
    private UserDto user;

    @JsonProperty(value = "qr_code")
    private String qrCode;

    @JsonProperty(value = "reputation")
    private Integer reputation;
}

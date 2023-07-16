package com.harera.hayat.model.blood;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.CommunicationMethod;

import lombok.Data;

@Data
public class BloodNeedUpdateRequest {

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("age")
    private int age;

    @JsonProperty("blood_type")
    private String bloodType;

    @JsonProperty("hospital")
    private String hospital;

    @JsonProperty("illness")
    private String illness;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty("communication_method")
    private CommunicationMethod communicationMethod;

    @JsonProperty("city_id")
    private Long cityId;
}

package com.harera.hayat.model.clothing;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ClothingDonationUpdateRequest extends ClothingDonationRequest {

    @JsonProperty("active")
    private boolean active;
}

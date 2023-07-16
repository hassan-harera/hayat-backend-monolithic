package com.harera.hayat.model.property;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.DonationDto;

import lombok.Data;

@Data
public class PropertyDonationDto extends DonationDto {

    private Integer rooms;
    private Integer bathrooms;
    private Integer kitchens;

    @JsonProperty("people_capacity")
    private Integer peopleCapacity;

    @JsonProperty("available_from")
    private OffsetDateTime availableFrom;

    @JsonProperty("available_to")
    private OffsetDateTime availableTo;

}

package com.harera.hayat.model.medicine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true,
                value = { "id", "active", "city", "user", "unit", "medicine" })
public class MedicineDonationUpdateRequest extends MedicineDonationDto {

}

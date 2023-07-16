package com.harera.hayat.model.medicine;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.DonationDto;

import lombok.Data;

@Data
public class MedicineDonationDto extends DonationDto {

    @JsonProperty("quantity")
    private Float quantity;

    @JsonProperty("medicine_unit")
    private MedicineUnitDto medicineUnit;

    @JsonProperty("medicine_expiration_date")
    private LocalDate medicineExpirationDate;

    @JsonProperty("medicine")
    private MedicineDto medicine;

    @JsonProperty(value = "city_id")
    private Long cityId;

    @JsonProperty(value = "medicine_unit_id")
    private Long medicineUnitId;

    @JsonProperty(value = "medicine_id")
    private Long medicineId;
}

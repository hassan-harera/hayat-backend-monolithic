package com.harera.hayat.model.medicine;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.NeedDto;

import lombok.Data;

@Data
public class MedicineNeedDto extends NeedDto {

    @JsonProperty("quantity")
    private Float quantity;

    @JsonProperty("medicine_unit")
    private MedicineUnitDto medicineUnit;

    @JsonProperty("medicine")
    private MedicineDto medicine;
}

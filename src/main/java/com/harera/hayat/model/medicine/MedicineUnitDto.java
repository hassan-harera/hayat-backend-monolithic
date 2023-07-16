package com.harera.hayat.model.medicine;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.BaseEntityDto;

import lombok.Data;

@Data
public class MedicineUnitDto extends BaseEntityDto {

    @JsonProperty("arabic_name")
    private String arabicName;

    @JsonProperty("english_name")
    private String englishName;
}

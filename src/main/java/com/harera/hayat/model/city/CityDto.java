package com.harera.hayat.model.city;

import com.harera.hayat.model.BaseEntityDto;

import lombok.Data;

@Data
public class CityDto extends BaseEntityDto {

    private String arabicName;
    private String englishName;
}

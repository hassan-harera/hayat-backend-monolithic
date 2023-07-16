package com.harera.hayat.model.medicine;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.harera.hayat.model.Need;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document("medicine_need")
public class MedicineNeed extends Need {

    @Field(name = "quantity")
    private Float quantity;

    @Field(name = "medicine")
    private MedicineDto medicine;

    @Field(name = "medicine_unit")
    private MedicineUnitDto medicineUnit;
}

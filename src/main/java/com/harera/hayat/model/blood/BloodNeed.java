package com.harera.hayat.model.blood;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.harera.hayat.model.Need;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = "blood_needs")
public class BloodNeed extends Need {

    @Field("age")
    private int age;

    @Field("blood_type")
    private String bloodType;

    @Field("hospital")
    private String hospital;

    @Field("illness")
    private String illness;
}

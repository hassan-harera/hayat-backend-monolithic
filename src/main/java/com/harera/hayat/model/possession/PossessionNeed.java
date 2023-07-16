package com.harera.hayat.model.possession;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.harera.hayat.model.Need;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document("possession_need")
public class PossessionNeed extends Need {

    @Field(name = "possession_category")
    private PossessionCategory possessionCategory;

    @Field(name = "possession_condition")
    private PossessionCondition possessionCondition;
}

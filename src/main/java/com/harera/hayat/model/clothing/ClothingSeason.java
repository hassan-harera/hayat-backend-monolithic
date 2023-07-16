package com.harera.hayat.model.clothing;

import com.harera.hayat.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "clothing_season")
public class ClothingSeason extends BaseEntity {

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;

    @Enumerated(EnumType.STRING)
    @Column(name = "shortcut")
    private Season season;

    public enum Season {
        WINTER,
        SUMMER,
        AUTUMN,
        SPRING,
    }
}

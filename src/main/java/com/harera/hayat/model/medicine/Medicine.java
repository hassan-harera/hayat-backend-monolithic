package com.harera.hayat.model.medicine;

import com.harera.hayat.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "medicine")
public class Medicine extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private MedicineCategory category;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private MedicineUnit unit;

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "english_name")
    private String englishName;
}

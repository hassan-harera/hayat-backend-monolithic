package com.harera.hayat.model.city;

import com.harera.hayat.model.BaseEntity;

import jakarta.persistence.Basic;
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
@Table(name = "city")
public class City extends BaseEntity {

    @Basic
    @Column(name = "arabic_name")
    private String arabicName;
    @Basic
    @Column(name = "english_name")
    private String englishName;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;
}

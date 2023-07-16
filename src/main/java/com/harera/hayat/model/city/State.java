package com.harera.hayat.model.city;

import java.util.List;

import com.harera.hayat.model.BaseEntity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "state")
public class State extends BaseEntity {

    @Basic
    @Column(name = "arabic_name")
    private String arabicName;

    @Basic
    @Column(name = "english_name")
    private String englishName;

    @OneToMany(mappedBy = "state")
    private List<City> cities;
}

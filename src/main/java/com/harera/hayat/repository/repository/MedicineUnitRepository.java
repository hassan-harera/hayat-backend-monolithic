package com.harera.hayat.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.medicine.MedicineUnit;

@Repository
public interface MedicineUnitRepository extends JpaRepository<MedicineUnit, Long> {

}

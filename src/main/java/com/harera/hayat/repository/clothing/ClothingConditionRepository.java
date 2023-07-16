package com.harera.hayat.repository.clothing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.clothing.ClothingCondition;

@Repository
public interface ClothingConditionRepository
                extends JpaRepository<ClothingCondition, Long> {
}

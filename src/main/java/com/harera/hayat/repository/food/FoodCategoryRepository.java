package com.harera.hayat.repository.food;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harera.hayat.model.food.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}

package com.harera.hayat.service.clothing;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.clothing.ClothingCategory;
import com.harera.hayat.repository.clothing.ClothingCategoryRepository;

@Service
public class ClothingCategoryService {

    private final ClothingCategoryRepository clothingConditionRepository;

    public ClothingCategoryService(
                    ClothingCategoryRepository clothingConditionRepository) {
        this.clothingConditionRepository = clothingConditionRepository;
    }

    @Cacheable("clothing_categories")
    public List<ClothingCategory> list() {
        return clothingConditionRepository.findAll();
    }

    public ClothingCategory get(Long clothingCategoryId) {
        return clothingConditionRepository.findById(clothingCategoryId).orElseThrow(
                        () -> new EntityNotFoundException("Clothing category not found"));
    }
}

package com.harera.hayat.service.clothing;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.clothing.ClothingCondition;
import com.harera.hayat.repository.clothing.ClothingConditionRepository;

@Service
public class ClothingConditionService {

    private final ClothingConditionRepository clothingConditionRepository;

    public ClothingConditionService(
                    ClothingConditionRepository clothingConditionRepository) {
        this.clothingConditionRepository = clothingConditionRepository;
    }

    @Cacheable("clothing_conditions")
    public List<ClothingCondition> list() {
        return clothingConditionRepository.findAll();
    }

    public ClothingCondition get(Long clothingConditionId) {
        return clothingConditionRepository.findById(clothingConditionId).orElseThrow(
                        () -> new RuntimeException("Clothing condition not found"));
    }
}

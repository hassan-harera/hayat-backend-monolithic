package com.harera.hayat.service.clothing;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.clothing.ClothingType;
import com.harera.hayat.repository.clothing.ClothingTypeRepository;

@Service
public class ClothingTypeService {

    private final ClothingTypeRepository clothingTypeRepository;

    public ClothingTypeService(ClothingTypeRepository clothingTypeRepository) {
        this.clothingTypeRepository = clothingTypeRepository;
    }

    @Cacheable("clothing_types")
    public List<ClothingType> list() {
        return clothingTypeRepository.findAll();
    }

    public ClothingType get(Long clothingTypeId) {
        return clothingTypeRepository.findById(clothingTypeId).orElseThrow(
                        () -> new RuntimeException("Clothing type not found"));
    }
}

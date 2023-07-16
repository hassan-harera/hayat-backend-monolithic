package com.harera.hayat.service.clothing;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.clothing.ClothingSize;
import com.harera.hayat.repository.clothing.ClothingSizeRepository;

@Service
public class ClothingSizeService {

    private final ClothingSizeRepository clothingSizeRepository;

    public ClothingSizeService(ClothingSizeRepository clothingSizeRepository) {
        this.clothingSizeRepository = clothingSizeRepository;
    }

    @Cacheable("clothing_sizes")
    public List<ClothingSize> list() {
        return clothingSizeRepository.findAll();
    }

    public ClothingSize get(Long clothingSizeId) {
        return clothingSizeRepository.findById(clothingSizeId).orElseThrow(
                        () -> new EntityNotFoundException("Clothing size not found"));
    }
}

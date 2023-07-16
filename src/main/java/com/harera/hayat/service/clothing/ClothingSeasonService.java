package com.harera.hayat.service.clothing;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.clothing.ClothingSeason;
import com.harera.hayat.repository.clothing.ClothingSeasonRepository;

@Service
public class ClothingSeasonService {

    private final ClothingSeasonRepository clothingSeasonRepository;

    public ClothingSeasonService(ClothingSeasonRepository clothingSeasonRepository) {
        this.clothingSeasonRepository = clothingSeasonRepository;
    }

    @Cacheable(value = "clothing_seasons")
    public List<ClothingSeason> list() {
        return clothingSeasonRepository.findAll();
    }

    public ClothingSeason get(Long clothingSeasonId) {
        return clothingSeasonRepository.findById(clothingSeasonId).orElseThrow(
                        () -> new RuntimeException("Clothing season not found"));
    }
}

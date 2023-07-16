package com.harera.hayat.service.food;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.food.FoodCategory;
import com.harera.hayat.model.food.FoodCategoryResponse;
import com.harera.hayat.model.food.FoodUnit;
import com.harera.hayat.repository.food.FoodCategoryRepository;

@Service
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final ModelMapper modelMapper;

    public FoodCategoryService(FoodCategoryRepository foodCategoryRepository,
                    ModelMapper modelMapper) {
        this.foodCategoryRepository = foodCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Cacheable("food_categories#id")
    public FoodCategoryResponse get(Long id) {
        FoodCategory category = foodCategoryRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(FoodUnit.class, id));
        return modelMapper.map(category, FoodCategoryResponse.class);
    }

    @Cacheable("food_categories")
    public List<FoodCategoryResponse> list() {
        List<FoodCategory> foodUnitList = foodCategoryRepository.findAll();
        List<FoodCategoryResponse> list = new LinkedList<>();
        for (FoodCategory foodUnit : foodUnitList) {
            list.add(modelMapper.map(foodUnit, FoodCategoryResponse.class));
        }
        return list;
    }
}

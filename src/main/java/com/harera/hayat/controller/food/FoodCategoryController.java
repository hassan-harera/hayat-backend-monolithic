package com.harera.hayat.controller.food;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.food.FoodCategoryResponse;
import com.harera.hayat.service.food.FoodCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/v1/food/categories")
public class FoodCategoryController {

    private final FoodCategoryService foodCategoryService;

    public FoodCategoryController(FoodCategoryService foodCategoryService) {
        this.foodCategoryService = foodCategoryService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Get", description = "Get food category", tags = "Food",
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public FoodCategoryResponse getFood(@PathVariable("id") Long id) {
        return foodCategoryService.get(id);
    }

    @GetMapping
    @Operation(summary = "List", description = "List food units", tags = "Food",
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public Iterable<FoodCategoryResponse> listFood() {
        return foodCategoryService.list();
    }
}

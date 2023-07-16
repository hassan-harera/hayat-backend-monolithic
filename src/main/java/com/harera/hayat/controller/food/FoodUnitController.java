package com.harera.hayat.controller.food;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.food.FoodUnitResponse;
import com.harera.hayat.service.food.FoodUnitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/v1/food/units")
public class FoodUnitController {

    private final FoodUnitService foodUnitService;

    public FoodUnitController(FoodUnitService foodUnitService) {
        this.foodUnitService = foodUnitService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Get", description = "Get food units", tags = "Food",
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public FoodUnitResponse getFood(@PathVariable("id") Long id) {
        return foodUnitService.get(id);
    }

    @GetMapping
    @Operation(summary = "List", description = "List food units", tags = "Food",
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public Iterable<FoodUnitResponse> listFood() {
        return foodUnitService.list();
    }
}

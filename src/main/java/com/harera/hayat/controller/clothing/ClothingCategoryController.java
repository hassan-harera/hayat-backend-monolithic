package com.harera.hayat.controller.clothing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.clothing.ClothingCategory;
import com.harera.hayat.service.clothing.ClothingCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Clothing-Category")
@RestController
@RequestMapping("/api/v1/clothing/categories")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClothingCategoryController {

    private final ClothingCategoryService clothingCategoryService;

    @GetMapping
    @Operation(summary = "List", description = "List clothing categories",
                    tags = "Clothing-Category",
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<List<ClothingCategory>> list() {
        return ResponseEntity.ok(clothingCategoryService.list());
    }
}

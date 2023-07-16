package com.harera.hayat.controller.clothing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.clothing.ClothingType;
import com.harera.hayat.service.clothing.ClothingTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/clothing/types")
@Tag(name = "Clothing - Type")
public class ClothingTypeController {

    private final ClothingTypeService clothingConditionService;

    @GetMapping
    @Operation(summary = "Get", description = "List clothing types", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok") })
    public ResponseEntity<List<ClothingType>> list() {
        List<ClothingType> list = clothingConditionService.list();
        return ResponseEntity.ok(list);
    }
}

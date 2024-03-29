package com.harera.hayat.controller.clothing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.clothing.ClothingCondition;
import com.harera.hayat.service.clothing.ClothingConditionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/clothing/conditions")
@Tag(name = "Clothing - Condition")
public class ClothingConditionController {

    private final ClothingConditionService clothingConditionService;

    @GetMapping
    @Operation(summary = "Get", description = "List clothing conditions", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok") })
    public ResponseEntity<List<ClothingCondition>> list() {
        List<ClothingCondition> list = clothingConditionService.list();
        return ResponseEntity.ok(list);
    }
}

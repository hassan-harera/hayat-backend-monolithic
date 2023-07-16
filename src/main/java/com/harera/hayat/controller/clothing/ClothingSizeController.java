package com.harera.hayat.controller.clothing;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.service.clothing.ClothingSizeService;
import com.harera.hayat.model.clothing.ClothingSize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/clothing/sizes")
@Tag(name = "Clothing - Size")
public class ClothingSizeController {

    private final ClothingSizeService clothingConditionService;

    @GetMapping
    @Operation(summary = "Get", description = "List clothing sizes", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok") })
    public ResponseEntity<List<ClothingSize>> list() {
        List<ClothingSize> list = clothingConditionService.list();
        return ok(list);
    }
}

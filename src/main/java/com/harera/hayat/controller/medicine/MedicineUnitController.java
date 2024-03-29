package com.harera.hayat.controller.medicine;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.medicine.MedicineUnitDto;
import com.harera.hayat.service.medicine.MedicineUnitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Medicine Unit", description = "Medicine Unit API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/medicine/units")
public class MedicineUnitController {

    private final MedicineUnitService medicineUnitService;

    @GetMapping
    @Operation(summary = "List", description = "List medicine units",
                    tags = { "Medicine Unit" },
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<List<MedicineUnitDto>> list() {
        return ResponseEntity.ok(medicineUnitService.list());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get medicine units",
                    tags = { "Medicine Unit" },
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<MedicineUnitDto> get(@PathVariable("id") long id) {
        return ResponseEntity.ok(medicineUnitService.get(id));
    }
}

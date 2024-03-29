package com.harera.hayat.controller.medicine;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.medicine.MedicineDto;
import com.harera.hayat.service.medicine.MedicineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Medicine", description = "Medicines API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping
    @Operation(summary = "List", description = "List medicines", tags = { "Medicine" },
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<List<MedicineDto>> list(
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(medicineService.list(page));
    }

    @GetMapping("/search")
    @Operation(summary = "Search", description = "Search medicines",
                    tags = { "Medicine" },
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<List<MedicineDto>> search(
                    @RequestParam(value = "q", defaultValue = "") String query,
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(medicineService.search(query, page));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get medicine", tags = { "Medicine" },
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<MedicineDto> get(@PathVariable("id") long id) {
        return ResponseEntity.ok(medicineService.get(id));
    }
}

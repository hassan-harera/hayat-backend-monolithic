package com.harera.hayat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.NeedDto;
import com.harera.hayat.service.NeedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Needs")
@RestController
@RequestMapping("/api/v1/needs")
public class NeedController {

    private final NeedService needService;

    public NeedController(NeedService needService) {
        this.needService = needService;
    }

    @GetMapping("/results")
    @Operation(summary = "Search", description = "Search needs", tags = "Needs",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<NeedDto>> search(
                    @RequestParam(value = "q", defaultValue = "") String query,
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        List<NeedDto> needList = needService.search(query, page);
        return ResponseEntity.ok(needList);
    }
}

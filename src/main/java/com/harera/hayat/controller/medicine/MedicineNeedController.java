package com.harera.hayat.controller.medicine;

import java.util.List;

import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.model.medicine.MedicineNeedRequest;
import com.harera.hayat.model.medicine.MedicineNeedResponse;
import com.harera.hayat.model.medicine.MedicineNeedUpdateRequest;
import com.harera.hayat.service.medicine.MedicineNeedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/needs/medicine")
@Tag(name = "Medicine-Need")
public class MedicineNeedController {

    private final MedicineNeedService medicineNeedService;

    public MedicineNeedController(MedicineNeedService needService) {
        this.medicineNeedService = needService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a medicine needs",
                    tags = "Medicine-Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineNeedResponse> create(
                    @RequestBody MedicineNeedRequest medicineNeedRequest,
                    @RequestHeader("Authorization") String authorization) {
        MedicineNeedResponse needResponse = medicineNeedService
                        .create(medicineNeedRequest, authorization.substring(7));
        return ResponseEntity.ok(needResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update a medicine need",
                    tags = "Medicine-Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineNeedResponse> update(@PathVariable("id") String id,
                    @RequestBody MedicineNeedUpdateRequest medicineNeedUpdateRequest) {
        return ResponseEntity
                        .ok(medicineNeedService.update(id, medicineNeedUpdateRequest));
    }

    @GetMapping
    @Operation(summary = "List", description = "List medicine needs",
                    tags = "Medicine-Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<MedicineNeedResponse>> list(
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        List<MedicineNeedResponse> medicineNeedResponses = medicineNeedService.list(page);
        return ResponseEntity.ok(medicineNeedResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a medicine needs",
                    tags = "Medicine-Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineNeedResponse> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(medicineNeedService.get(id));
    }

    @GetMapping("/results")
    @Operation(summary = "List", description = "Search medicine needs",
                    tags = "Medicine-Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<MedicineNeedResponse>> search(
                    @RequestParam(value = "page", defaultValue = "1") int page,
                    @RequestParam(value = "q", defaultValue = "") String query) {
        List<MedicineNeedResponse> medicineNeedResponses =
                        medicineNeedService.search(query, page);
        return ResponseEntity.ok(medicineNeedResponses);
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Insert Image", description = "Insert image for a medicine need",
                    tags = "Medicine-Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineNeedResponse> insertImage(
                    @RequestPart(name = "file") MultipartFile file,
                    @PathVariable("id") String id) {
        return ResponseEntity.ok(medicineNeedService.updateImage(id, file));
    }

    @PutMapping("/{id}/upvote")
    @Operation(summary = "Upvote Book Need", description = "Upvote Book Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "Invalid request body"),
                            @ApiResponse(responseCode = "404",
                                            description = "Book Need not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Medicine-Need")
    public ResponseEntity<Void> upvoteBloodNeed(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        medicineNeedService.upvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/down-vote")
    @Operation(summary = "Down-vote Blood Need", description = "Down-vote Blood Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "404",
                                            description = "Book Need not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Medicine-Need")
    public ResponseEntity<Void> downVoteBloodNeed(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        medicineNeedService.downvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }
}

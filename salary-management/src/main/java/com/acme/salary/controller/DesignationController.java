package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.DesignationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/designations")
@Tag(name = "Designations", description = "Create and manage designations")
public class DesignationController {

    private final DesignationService designationService;

    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    @PostMapping("/addDesignation")
    @Operation(summary = "Add designation")
    public ResponseEntity<EmployeeResponse> createDesignation(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                designationService.createDesignation(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/updateDesignation/{id}")
    @Operation(summary = "Update designation")
    public ResponseEntity<EmployeeResponse> updateDesignation(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                designationService.updateDesignation(id, request));
    }

    @GetMapping("/getDesignation/{id}")
    @Operation(summary = "Get designation by ID")
    public ResponseEntity<EmployeeResponse> getDesignationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                designationService.getDesignationById(id));
    }

    @GetMapping("/getAllDesignations")
    @Operation(summary = "List all designations")
    public ResponseEntity<List<EmployeeResponse>> getAllDesignations() {

        return ResponseEntity.ok(
                designationService.getAllDesignations());
    }

    @DeleteMapping("/deleteDesignationById/{id}")
    @Operation(summary = "Delete designation")
    public ResponseEntity<Void> deleteDesignation(
            @PathVariable Long id) {

        designationService.deleteDesignation(id);
        return ResponseEntity.noContent().build();
    }
}

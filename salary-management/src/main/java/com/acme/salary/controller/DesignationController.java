package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.DesignationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designations")
public class DesignationController {

    private final DesignationService designationService;

    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createDesignation(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                designationService.createDesignation(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateDesignation(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                designationService.updateDesignation(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getDesignationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                designationService.getDesignationById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllDesignations() {

        return ResponseEntity.ok(
                designationService.getAllDesignations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesignation(
            @PathVariable Long id) {

        designationService.deleteDesignation(id);
        return ResponseEntity.noContent().build();
    }
}
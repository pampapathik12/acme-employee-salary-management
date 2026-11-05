package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.DepartmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Departments", description = "Create and manage departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/addDepartment")
    @Operation(summary = "Add department")
    public ResponseEntity<EmployeeResponse> createDepartment(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                departmentService.createDepartment(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/updateDepartment/{id}")
    @Operation(summary = "Update department")
    public ResponseEntity<EmployeeResponse> updateDepartment(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                departmentService.updateDepartment(id, request));
    }

    @GetMapping("/getDepartment/{id}")
    @Operation(summary = "Get department by ID")
    public ResponseEntity<EmployeeResponse> getDepartmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                departmentService.getDepartmentById(id));
    }

    @GetMapping("/getAllDepartments")
    @Operation(summary = "List all departments")
    public ResponseEntity<List<EmployeeResponse>> getAllDepartments() {

        return ResponseEntity.ok(
                departmentService.getAllDepartments());
    }

    @DeleteMapping("/deleteDepartmentById/{id}")
    @Operation(summary = "Delete department")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}

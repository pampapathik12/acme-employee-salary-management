package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.DepartmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createDepartment(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                departmentService.createDepartment(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateDepartment(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                departmentService.updateDepartment(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getDepartmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                departmentService.getDepartmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllDepartments() {

        return ResponseEntity.ok(
                departmentService.getAllDepartments());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
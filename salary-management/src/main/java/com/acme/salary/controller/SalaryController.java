package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.SalaryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
@Tag(name = "Salaries", description = "Manage employee salary assignments")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping("/addSalary")
    @Operation(summary = "Add salary", description = "Assign a salary component and amount to an employee")
    public ResponseEntity<EmployeeResponse> createSalary(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                salaryService.createSalary(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/updateSalary/{id}")
    @Operation(summary = "Update salary", description = "Update an existing salary assignment")
    public ResponseEntity<EmployeeResponse> updateSalary(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                salaryService.updateSalary(id, request));
    }

    @GetMapping("/getSalary/{id}")
    @Operation(summary = "Get salary by ID", description = "Get one salary assignment by its assignment ID")
    public ResponseEntity<EmployeeResponse> getSalaryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                salaryService.getSalaryById(id));
    }

    @GetMapping("/getAllSalaries")
    @Operation(summary = "List all salaries", description = "Get all employee salary assignments")
    public ResponseEntity<List<EmployeeResponse>> getAllSalaries() {

        return ResponseEntity.ok(
                salaryService.getAllSalaries());
    }

    @GetMapping("/getSalariesByEmployeeId/{employeeId}")
    @Operation(summary = "List salaries for employee", description = "Get all salary assignments for one employee")
    public ResponseEntity<List<EmployeeResponse>> getSalariesByEmployeeId(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                salaryService.getSalariesByEmployeeId(employeeId));
    }

    @DeleteMapping("/deleteSalaryById/{id}")
    @Operation(summary = "Delete salary", description = "Delete a salary assignment by its assignment ID")
    public ResponseEntity<Void> deleteSalary(
            @PathVariable Long id) {

        salaryService.deleteSalary(id);
        return ResponseEntity.noContent().build();
    }
}

package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.SalaryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createSalary(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                salaryService.createSalary(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateSalary(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                salaryService.updateSalary(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getSalaryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                salaryService.getSalaryById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllSalaries() {

        return ResponseEntity.ok(
                salaryService.getAllSalaries());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeResponse>> getSalariesByEmployeeId(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                salaryService.getSalariesByEmployeeId(employeeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalary(
            @PathVariable Long id) {

        salaryService.deleteSalary(id);
        return ResponseEntity.noContent().build();
    }
}
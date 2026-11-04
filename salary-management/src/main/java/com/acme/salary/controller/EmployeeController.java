package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                employeeService.createEmployee(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {

        return ResponseEntity.ok(
                employeeService.getAllEmployees());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
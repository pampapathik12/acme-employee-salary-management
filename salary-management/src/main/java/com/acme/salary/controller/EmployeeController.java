package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "Create and manage employee records")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/addEmployee")
    @Operation(summary = "Add employee")
    public ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                employeeService.createEmployee(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/updateEmployee/{id}")
    @Operation(summary = "Update employee")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, request));
    }

    @GetMapping("/getEmployee/{id}")
    @Operation(summary = "Get employee by ID")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id));
    }

    @GetMapping("/getAllEmployees")
    @Operation(summary = "List all employees")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {

        return ResponseEntity.ok(
                employeeService.getAllEmployees());
    }

    @DeleteMapping("/deleteEmployeeById/{id}")
    @Operation(summary = "Delete employee")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}

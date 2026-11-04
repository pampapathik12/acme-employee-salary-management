package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.LeaveService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> applyLeave(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                leaveService.applyLeave(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateLeave(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                leaveService.updateLeave(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getLeaveById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                leaveService.getLeaveById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllLeaves() {

        return ResponseEntity.ok(
                leaveService.getAllLeaves());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeResponse>> getLeavesByEmployeeId(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                leaveService.getLeavesByEmployeeId(employeeId));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<EmployeeResponse> approveLeave(
            @PathVariable Long id,
            @RequestParam Long approvedBy) {

        return ResponseEntity.ok(
                leaveService.approveLeave(id, approvedBy));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<EmployeeResponse> rejectLeave(
            @PathVariable Long id,
            @RequestParam Long approvedBy) {

        return ResponseEntity.ok(
                leaveService.rejectLeave(id, approvedBy));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(
            @PathVariable Long id) {

        leaveService.deleteLeave(id);
        return ResponseEntity.noContent().build();
    }
}
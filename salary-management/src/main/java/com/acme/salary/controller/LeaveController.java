package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.LeaveService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@Tag(name = "Leaves", description = "Submit and manage employee leave requests")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/applyLeave")
    @Operation(summary = "Apply for leave")
    public ResponseEntity<EmployeeResponse> applyLeave(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                leaveService.applyLeave(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/updateLeave/{id}")
    @Operation(summary = "Update leave request")
    public ResponseEntity<EmployeeResponse> updateLeave(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                leaveService.updateLeave(id, request));
    }

    @GetMapping("/getLeave/{id}")
    @Operation(summary = "Get leave request by ID")
    public ResponseEntity<EmployeeResponse> getLeaveById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                leaveService.getLeaveById(id));
    }

    @GetMapping("/getAllLeaves")
    @Operation(summary = "List all leave requests")
    public ResponseEntity<List<EmployeeResponse>> getAllLeaves() {

        return ResponseEntity.ok(
                leaveService.getAllLeaves());
    }

    @GetMapping("/getLeavesByEmployeeId/{employeeId}")
    @Operation(summary = "List leave requests for employee")
    public ResponseEntity<List<EmployeeResponse>> getLeavesByEmployeeId(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                leaveService.getLeavesByEmployeeId(employeeId));
    }

    @PutMapping("/approveLeave/{id}")
    @Operation(summary = "Approve leave request")
    public ResponseEntity<EmployeeResponse> approveLeave(
            @PathVariable Long id,
            @AuthenticationPrincipal String authenticatedEmail) {

        return ResponseEntity.ok(
                leaveService.approveLeave(id, authenticatedEmail));
    }

    @PutMapping("/rejectLeave/{id}")
    @Operation(summary = "Reject leave request")
    public ResponseEntity<EmployeeResponse> rejectLeave(
            @PathVariable Long id,
            @AuthenticationPrincipal String authenticatedEmail) {

        return ResponseEntity.ok(
                leaveService.rejectLeave(id, authenticatedEmail));
    }

    @DeleteMapping("/deleteLeaveById/{id}")
    @Operation(summary = "Delete leave request")
    public ResponseEntity<Void> deleteLeave(
            @PathVariable Long id) {

        leaveService.deleteLeave(id);
        return ResponseEntity.noContent().build();
    }
}

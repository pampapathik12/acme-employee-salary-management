package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.AttendanceService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@Tag(name = "Attendance", description = "Record and manage employee attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/markAttendance")
    @Operation(summary = "Mark attendance")
    public ResponseEntity<EmployeeResponse> markAttendance(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                attendanceService.markAttendance(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/updateAttendance/{id}")
    @Operation(summary = "Update attendance")
    public ResponseEntity<EmployeeResponse> updateAttendance(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                attendanceService.updateAttendance(id, request));
    }

    @GetMapping("/getAttendance/{id}")
    @Operation(summary = "Get attendance by ID")
    public ResponseEntity<EmployeeResponse> getAttendanceById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceById(id));
    }

    @GetMapping("/getAllAttendance")
    @Operation(summary = "List all attendance records")
    public ResponseEntity<List<EmployeeResponse>> getAllAttendance() {

        return ResponseEntity.ok(
                attendanceService.getAllAttendance());
    }

    @GetMapping("/getAttendanceByEmployeeId/{employeeId}")
    @Operation(summary = "List attendance for employee")
    public ResponseEntity<List<EmployeeResponse>> getAttendanceByEmployeeId(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByEmployeeId(employeeId));
    }

    @DeleteMapping("/deleteAttendanceById/{id}")
    @Operation(summary = "Delete attendance record")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable Long id) {

        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}

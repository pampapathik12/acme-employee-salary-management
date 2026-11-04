package com.acme.salary.controller;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.AttendanceService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> markAttendance(
            @RequestBody EmployeeRequest request) {

        return new ResponseEntity<>(
                attendanceService.markAttendance(request),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateAttendance(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                attendanceService.updateAttendance(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getAttendanceById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllAttendance() {

        return ResponseEntity.ok(
                attendanceService.getAllAttendance());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeResponse>> getAttendanceByEmployeeId(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByEmployeeId(employeeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable Long id) {

        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
package com.acme.salary.service.impl;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.entity.Attendance;
import com.acme.salary.repository.AttendanceRepository;
import com.acme.salary.repository.EmployeeRepository;
import com.acme.salary.service.AttendanceService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeResponse markAttendance(EmployeeRequest request) {
        throw new UnsupportedOperationException(
                "EmployeeRequest does not contain attendance fields. AttendanceRequest is required.");
    }

    @Override
    public EmployeeResponse updateAttendance(Long id, EmployeeRequest request) {
        throw new UnsupportedOperationException(
                "EmployeeRequest does not contain attendance fields. AttendanceRequest is required.");
    }

    @Override
    public EmployeeResponse getAttendanceById(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Attendance not found with ID: " + id));

        return mapToResponse(attendance);
    }

    @Override
    public List<EmployeeResponse> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> getAttendanceByEmployeeId(Long employeeId) {

        return attendanceRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteAttendance(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Attendance not found with ID: " + id));

        attendanceRepository.delete(attendance);
    }

    private EmployeeResponse mapToResponse(Attendance attendance) {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(attendance.getId());

        if (attendance.getEmployee() != null) {
            response.setFirstName(attendance.getEmployee().getFirstName());
            response.setLastName(attendance.getEmployee().getLastName());
        }

        return response;
    }
}
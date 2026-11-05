package com.acme.salary.service.impl;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.entity.Attendance;
import com.acme.salary.repository.AttendanceRepository;
import com.acme.salary.repository.EmployeeRepository;
import com.acme.salary.service.AttendanceService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import java.util.List;

@Service
@Transactional
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
        Attendance attendance = new Attendance();
        apply(attendance, request, true);
        return mapToResponse(attendanceRepository.save(attendance));
    }

    @Override
    public EmployeeResponse updateAttendance(Long id, EmployeeRequest request) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendance not found: " + id));
        apply(attendance, request, false);
        return mapToResponse(attendanceRepository.save(attendance));
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
        response.setEmployeeId(attendance.getEmployee().getId());
        response.setAttendanceDate(attendance.getAttendanceDate());
        response.setCheckIn(attendance.getCheckIn());
        response.setCheckOut(attendance.getCheckOut());
        response.setStatus(attendance.getStatus());

        if (attendance.getEmployee() != null) {
            response.setFirstName(attendance.getEmployee().getFirstName());
            response.setLastName(attendance.getEmployee().getLastName());
        }

        return response;
    }

    private void apply(Attendance attendance, EmployeeRequest request, boolean creating) {
        if (request.getEmployeeId() != null) {
            attendance.setEmployee(employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + request.getEmployeeId())));
        } else if (creating) {
            throw new IllegalArgumentException("employeeId is required; attendance is recorded for an existing employee");
        }
        if (request.getAttendanceDate() != null) attendance.setAttendanceDate(request.getAttendanceDate());
        else if (creating) attendance.setAttendanceDate(LocalDate.now());
        if (request.getCheckIn() != null) attendance.setCheckIn(request.getCheckIn());
        if (request.getCheckOut() != null) attendance.setCheckOut(request.getCheckOut());
        if (request.getStatus() != null) attendance.setStatus(request.getStatus());
        else if (creating) attendance.setStatus("PRESENT");
    }
}

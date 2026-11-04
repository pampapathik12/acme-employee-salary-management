package com.acme.salary.service;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;

import java.util.List;

public interface AttendanceService {

    EmployeeResponse markAttendance(EmployeeRequest request);

    EmployeeResponse updateAttendance(Long id, EmployeeRequest request);

    EmployeeResponse getAttendanceById(Long id);

    List<EmployeeResponse> getAllAttendance();

    List<EmployeeResponse> getAttendanceByEmployeeId(Long employeeId);

    void deleteAttendance(Long id);
}
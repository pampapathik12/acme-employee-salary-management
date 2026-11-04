package com.acme.salary.service;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getAllEmployees();

    void deleteEmployee(Long id);
}
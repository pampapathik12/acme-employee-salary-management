package com.acme.salary.service;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;

import java.util.List;

public interface DepartmentService {

    EmployeeResponse createDepartment(EmployeeRequest request);

    EmployeeResponse updateDepartment(Long id, EmployeeRequest request);

    EmployeeResponse getDepartmentById(Long id);

    List<EmployeeResponse> getAllDepartments();

    void deleteDepartment(Long id);
}
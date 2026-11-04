package com.acme.salary.service;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;

import java.util.List;

public interface SalaryService {

    EmployeeResponse createSalary(EmployeeRequest request);

    EmployeeResponse updateSalary(Long id, EmployeeRequest request);

    EmployeeResponse getSalaryById(Long id);

    List<EmployeeResponse> getAllSalaries();

    List<EmployeeResponse> getSalariesByEmployeeId(Long employeeId);

    void deleteSalary(Long id);

}
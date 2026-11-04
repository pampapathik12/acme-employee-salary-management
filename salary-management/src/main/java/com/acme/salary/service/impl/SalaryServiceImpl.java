package com.acme.salary.service.impl;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.SalaryService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Override
    public EmployeeResponse createSalary(EmployeeRequest request) {

        throw new UnsupportedOperationException(
                "Employee salary component entity and repository mapping required");
    }

    @Override
    public EmployeeResponse updateSalary(Long id, EmployeeRequest request) {

        throw new UnsupportedOperationException(
                "Employee salary component entity and repository mapping required");
    }

    @Override
    public EmployeeResponse getSalaryById(Long id) {

        throw new UnsupportedOperationException(
                "Employee salary component entity and repository mapping required");
    }

    @Override
    public List<EmployeeResponse> getAllSalaries() {

        throw new UnsupportedOperationException(
                "Employee salary component entity and repository mapping required");
    }

    @Override
    public List<EmployeeResponse> getSalariesByEmployeeId(Long employeeId) {

        throw new UnsupportedOperationException(
                "Employee salary component entity and repository mapping required");
    }

    @Override
    public void deleteSalary(Long id) {

        throw new UnsupportedOperationException(
                "Employee salary component entity and repository mapping required");
    }
}
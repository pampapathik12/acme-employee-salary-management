package com.acme.salary.service;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;

import java.util.List;

public interface DesignationService {

    EmployeeResponse createDesignation(EmployeeRequest request);

    EmployeeResponse updateDesignation(Long id, EmployeeRequest request);

    EmployeeResponse getDesignationById(Long id);

    List<EmployeeResponse> getAllDesignations();

    void deleteDesignation(Long id);
}
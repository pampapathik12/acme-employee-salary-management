package com.acme.salary.service;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;

import java.util.List;

public interface LeaveService {

    EmployeeResponse applyLeave(EmployeeRequest request);

    EmployeeResponse updateLeave(Long id, EmployeeRequest request);

    EmployeeResponse getLeaveById(Long id);

    List<EmployeeResponse> getAllLeaves();

    List<EmployeeResponse> getLeavesByEmployeeId(Long employeeId);

    EmployeeResponse approveLeave(Long id, String authenticatedEmail);

    EmployeeResponse rejectLeave(Long id, String authenticatedEmail);

    void deleteLeave(Long id);

}

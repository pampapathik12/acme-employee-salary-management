package com.acme.salary.service.impl;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.service.LeaveService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Override
    public EmployeeResponse applyLeave(EmployeeRequest request) {

        throw new UnsupportedOperationException(
                "Leave entity and repository mapping required");
    }

    @Override
    public EmployeeResponse updateLeave(Long id, EmployeeRequest request) {

        throw new UnsupportedOperationException(
                "Leave entity and repository mapping required");
    }

    @Override
    public EmployeeResponse getLeaveById(Long id) {

        throw new UnsupportedOperationException(
                "Leave entity and repository mapping required");
    }

    @Override
    public List<EmployeeResponse> getAllLeaves() {

        throw new UnsupportedOperationException(
                "Leave entity and repository mapping required");
    }

    @Override
    public List<EmployeeResponse> getLeavesByEmployeeId(Long employeeId) {

        throw new UnsupportedOperationException(
                "Leave entity and repository mapping required");
    }

    @Override
    public EmployeeResponse approveLeave(Long id, Long approvedBy) {

        throw new UnsupportedOperationException(
                "Leave entity and repository mapping required");
    }

    @Override
    public EmployeeResponse rejectLeave(Long id, Long approvedBy) {

        throw new UnsupportedOperationException(
                "Leave entity and repository mapping required");
    }

    @Override
    public void deleteLeave(Long id) {

        throw new UnsupportedOperationException(
                "Leave entity and repository mapping required");
    }
}
package com.acme.salary.service.impl;

import com.acme.salary.dto.PayrollResponse;
import com.acme.salary.service.PayrollService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Override
    public PayrollResponse generatePayroll(Long employeeId, Long payrollPeriodId) {

        throw new UnsupportedOperationException(
                "Payroll entity and repository mapping required");
    }

    @Override
    public PayrollResponse getPayrollById(Long id) {

        throw new UnsupportedOperationException(
                "Payroll entity and repository mapping required");
    }

    @Override
    public List<PayrollResponse> getAllPayrolls() {

        throw new UnsupportedOperationException(
                "Payroll entity and repository mapping required");
    }

    @Override
    public List<PayrollResponse> getPayrollsByEmployeeId(Long employeeId) {

        throw new UnsupportedOperationException(
                "Payroll entity and repository mapping required");
    }

    @Override
    public PayrollResponse updatePaymentStatus(Long id, String paymentStatus) {

        throw new UnsupportedOperationException(
                "Payroll entity and repository mapping required");
    }
}
package com.acme.salary.service;

import com.acme.salary.dto.PayrollResponse;

import java.util.List;

public interface PayrollService {

    PayrollResponse generatePayroll(Long employeeId, Long payrollPeriodId);

    PayrollResponse getPayrollById(Long id);

    List<PayrollResponse> getAllPayrolls();

    List<PayrollResponse> getPayrollsByEmployeeId(Long employeeId);

    PayrollResponse updatePaymentStatus(Long id, String paymentStatus);

}
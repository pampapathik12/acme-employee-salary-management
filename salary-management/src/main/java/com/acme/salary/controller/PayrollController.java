package com.acme.salary.controller;

import com.acme.salary.dto.PayrollResponse;
import com.acme.salary.service.PayrollService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping("/generate")
    public ResponseEntity<PayrollResponse> generatePayroll(
            @RequestParam Long employeeId,
            @RequestParam Long payrollPeriodId) {

        return new ResponseEntity<>(
                payrollService.generatePayroll(employeeId, payrollPeriodId),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollResponse> getPayrollById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                payrollService.getPayrollById(id));
    }

    @GetMapping
    public ResponseEntity<List<PayrollResponse>> getAllPayrolls() {

        return ResponseEntity.ok(
                payrollService.getAllPayrolls());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PayrollResponse>> getPayrollsByEmployeeId(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                payrollService.getPayrollsByEmployeeId(employeeId));
    }

    @PutMapping("/{id}/payment-status")
    public ResponseEntity<PayrollResponse> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam String paymentStatus) {

        return ResponseEntity.ok(
                payrollService.updatePaymentStatus(id, paymentStatus));
    }
}
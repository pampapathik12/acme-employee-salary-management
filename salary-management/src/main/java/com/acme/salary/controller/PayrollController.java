package com.acme.salary.controller;

import com.acme.salary.dto.PayrollResponse;
import com.acme.salary.service.PayrollService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@Tag(name = "Payroll", description = "Generate payroll and manage payroll payment status")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping("/generatePayroll")
    @Operation(summary = "Generate payroll for employee and period")
    public ResponseEntity<PayrollResponse> generatePayroll(
            @RequestParam Long employeeId,
            @RequestParam Long payrollPeriodId) {

        return new ResponseEntity<>(
                payrollService.generatePayroll(employeeId, payrollPeriodId),
                HttpStatus.CREATED);
    }

    @GetMapping("/getPayroll/{id}")
    @Operation(summary = "Get payroll by ID")
    public ResponseEntity<PayrollResponse> getPayrollById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                payrollService.getPayrollById(id));
    }

    @GetMapping("/getAllPayrolls")
    @Operation(summary = "List all payroll records")
    public ResponseEntity<List<PayrollResponse>> getAllPayrolls() {

        return ResponseEntity.ok(
                payrollService.getAllPayrolls());
    }

    @GetMapping("/getPayrollsByEmployeeId/{employeeId}")
    @Operation(summary = "List payroll records for employee")
    public ResponseEntity<List<PayrollResponse>> getPayrollsByEmployeeId(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                payrollService.getPayrollsByEmployeeId(employeeId));
    }

    @PutMapping("/updatePaymentStatus/{id}")
    @Operation(summary = "Update payroll payment status")
    public ResponseEntity<PayrollResponse> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam String paymentStatus) {

        return ResponseEntity.ok(
                payrollService.updatePaymentStatus(id, paymentStatus));
    }
}

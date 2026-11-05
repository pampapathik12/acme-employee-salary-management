package com.acme.salary.controller;

import com.acme.salary.dto.PayrollPeriodRequest;
import com.acme.salary.entity.PayrollPeriod;
import com.acme.salary.repository.PayrollPeriodRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll-periods")
@Tag(name = "Payroll periods", description = "Create and retrieve payroll periods")
public class PayrollPeriodController {
    private final PayrollPeriodRepository payrollPeriodRepository;

    public PayrollPeriodController(PayrollPeriodRepository payrollPeriodRepository) {
        this.payrollPeriodRepository = payrollPeriodRepository;
    }

    @PostMapping("/addPayrollPeriod")
    @Operation(summary = "Add payroll period")
    public ResponseEntity<PayrollPeriod> addPayrollPeriod(@RequestBody PayrollPeriodRequest request) {
        if (request.getPeriodName() == null || request.getPeriodName().isBlank())
            throw new IllegalArgumentException("periodName is required");
        if (request.getStartDate() == null || request.getEndDate() == null)
            throw new IllegalArgumentException("startDate and endDate are required");
        if (request.getEndDate().isBefore(request.getStartDate()))
            throw new IllegalArgumentException("endDate must be on or after startDate");

        PayrollPeriod period = new PayrollPeriod();
        period.setPeriodName(request.getPeriodName());
        period.setStartDate(request.getStartDate());
        period.setEndDate(request.getEndDate());
        period.setStatus(request.getStatus() == null || request.getStatus().isBlank()
                ? "OPEN" : request.getStatus().toUpperCase());
        return ResponseEntity.status(HttpStatus.CREATED).body(payrollPeriodRepository.save(period));
    }

    @GetMapping("/getAllPayrollPeriods")
    @Operation(summary = "List all payroll periods")
    public List<PayrollPeriod> getAllPayrollPeriods() {
        return payrollPeriodRepository.findAll();
    }

    @GetMapping("/getPayrollPeriod/{id}")
    @Operation(summary = "Get payroll period by ID")
    public PayrollPeriod getPayrollPeriod(@PathVariable Long id) {
        return payrollPeriodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payroll period not found: " + id));
    }
}

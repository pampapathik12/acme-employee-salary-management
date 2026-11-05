package com.acme.salary.service.impl;

import com.acme.salary.dto.PayrollResponse;
import com.acme.salary.entity.Employee;
import com.acme.salary.entity.EmployeeSalaryComponent;
import com.acme.salary.entity.Payroll;
import com.acme.salary.entity.PayrollPeriod;
import com.acme.salary.repository.EmployeeRepository;
import com.acme.salary.repository.EmployeeSalaryComponentRepository;
import com.acme.salary.repository.PayrollPeriodRepository;
import com.acme.salary.repository.PayrollRepository;
import com.acme.salary.service.PayrollService;

import java.util.List;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;
    private final PayrollPeriodRepository periodRepository;
    private final EmployeeSalaryComponentRepository salaryRepository;

    public PayrollServiceImpl(PayrollRepository payrollRepository, EmployeeRepository employeeRepository,
                              PayrollPeriodRepository periodRepository,
                              EmployeeSalaryComponentRepository salaryRepository) {
        this.payrollRepository = payrollRepository; this.employeeRepository = employeeRepository;
        this.periodRepository = periodRepository; this.salaryRepository = salaryRepository;
    }

    @Override
    public PayrollResponse generatePayroll(Long employeeId, Long payrollPeriodId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + employeeId));
        PayrollPeriod period = periodRepository.findById(payrollPeriodId)
                .orElseThrow(() -> new IllegalArgumentException("Payroll period not found: " + payrollPeriodId));
        if (payrollRepository.findByEmployeeIdAndPayrollPeriodId(employeeId, payrollPeriodId).size() > 0)
            throw new IllegalArgumentException("Payroll already exists for employee and period");
        BigDecimal basic = employee.getSalary() == null ? BigDecimal.ZERO : employee.getSalary();
        BigDecimal componentBasic = BigDecimal.ZERO;
        boolean hasBasicComponent = false;
        BigDecimal allowances = BigDecimal.ZERO; BigDecimal deductions = BigDecimal.ZERO;
        for (EmployeeSalaryComponent component : salaryRepository.findByEmployeeId(employeeId)) {
            String type = component.getSalaryComponent().getComponentType();
            if (type != null && type.equalsIgnoreCase("DEDUCTION")) deductions = deductions.add(component.getAmount());
            else if (type != null && type.equalsIgnoreCase("ALLOWANCE")) allowances = allowances.add(component.getAmount());
            else if (type != null && (type.equalsIgnoreCase("BASIC") || type.equalsIgnoreCase("BASE"))) {
                componentBasic = componentBasic.add(component.getAmount()); hasBasicComponent = true;
            }
        }
        if (hasBasicComponent) basic = componentBasic;
        BigDecimal gross = basic.add(allowances);
        Payroll payroll = new Payroll(); payroll.setEmployee(employee); payroll.setPayrollPeriod(period);
        payroll.setBasicSalary(basic); payroll.setGrossSalary(gross); payroll.setTotalDeductions(deductions); payroll.setNetSalary(gross.subtract(deductions));
        payroll.setStatus("PENDING");
        return map(payrollRepository.save(payroll));
    }

    @Override
    public PayrollResponse getPayrollById(Long id) {

        return map(find(id));
    }

    @Override
    public List<PayrollResponse> getAllPayrolls() {

        return payrollRepository.findAll().stream().map(this::map).toList();
    }

    @Override
    public List<PayrollResponse> getPayrollsByEmployeeId(Long employeeId) {

        return payrollRepository.findByEmployeeId(employeeId).stream().map(this::map).toList();
    }

    @Override
    public PayrollResponse updatePaymentStatus(Long id, String paymentStatus) {

        Payroll payroll = find(id);
        if (paymentStatus == null || !List.of("PENDING", "PAID", "FAILED", "CANCELLED").contains(paymentStatus.toUpperCase()))
            throw new IllegalArgumentException("paymentStatus must be PENDING, PAID, FAILED, or CANCELLED");
        payroll.setStatus(paymentStatus.toUpperCase());
        if ("PAID".equals(payroll.getStatus())) payroll.setPaymentDate(java.time.LocalDate.now());
        return map(payrollRepository.save(payroll));
    }

    private Payroll find(Long id) {
        return payrollRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Payroll not found: " + id));
    }
    private PayrollResponse map(Payroll payroll) {
        PayrollResponse r = new PayrollResponse(); Employee employee = payroll.getEmployee();
        r.setPayrollId(payroll.getId()); r.setEmployeeId(employee.getId());
        r.setEmployeeName(employee.getFirstName() + (employee.getLastName() == null ? "" : " " + employee.getLastName()));
        r.setDepartment(employee.getDepartment()); r.setBasicSalary(payroll.getBasicSalary());
        r.setAllowances(payroll.getGrossSalary().subtract(payroll.getBasicSalary() == null ? BigDecimal.ZERO : payroll.getBasicSalary()));
        r.setDeductions(payroll.getTotalDeductions()); r.setNetSalary(payroll.getNetSalary());
        r.setPaymentStatus(payroll.getStatus());
        r.setPaymentDate(payroll.getPaymentDate());
        return r;
    }
}

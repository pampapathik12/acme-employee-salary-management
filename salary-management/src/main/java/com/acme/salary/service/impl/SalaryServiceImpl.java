package com.acme.salary.service.impl;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.entity.Employee;
import com.acme.salary.entity.EmployeeSalaryComponent;
import com.acme.salary.entity.SalaryComponent;
import com.acme.salary.repository.EmployeeRepository;
import com.acme.salary.repository.EmployeeSalaryComponentRepository;
import com.acme.salary.repository.SalaryComponentRepository;
import com.acme.salary.service.SalaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {
    private final EmployeeSalaryComponentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final SalaryComponentRepository componentRepository;

    public SalaryServiceImpl(EmployeeSalaryComponentRepository assignmentRepository,
                             EmployeeRepository employeeRepository,
                             SalaryComponentRepository componentRepository) {
        this.assignmentRepository = assignmentRepository;
        this.employeeRepository = employeeRepository;
        this.componentRepository = componentRepository;
    }

    @Override public EmployeeResponse createSalary(EmployeeRequest request) {
        Employee employee = employee(request.getEmployeeId());
        SalaryComponent component = component(request);
        EmployeeSalaryComponent assignment = new EmployeeSalaryComponent();
        assignment.setEmployee(employee);
        assignment.setSalaryComponent(component);
        assignment.setAmount(request.getAmount() != null ? request.getAmount() : request.getSalary());
        if (assignment.getAmount() == null) throw new IllegalArgumentException("amount is required");
        return map(assignmentRepository.save(assignment));
    }

    @Override public EmployeeResponse updateSalary(Long id, EmployeeRequest request) {
        EmployeeSalaryComponent assignment = assignment(id);
        if (request.getEmployeeId() != null) assignment.setEmployee(employee(request.getEmployeeId()));
        if (request.getSalaryComponentId() != null || request.getComponentName() != null) assignment.setSalaryComponent(component(request));
        if (request.getAmount() != null || request.getSalary() != null)
            assignment.setAmount(request.getAmount() != null ? request.getAmount() : request.getSalary());
        return map(assignmentRepository.save(assignment));
    }

    @Override @Transactional(readOnly = true) public EmployeeResponse getSalaryById(Long id) { return map(assignment(id)); }
    @Override @Transactional(readOnly = true) public List<EmployeeResponse> getAllSalaries() {
        return assignmentRepository.findAll().stream().map(this::map).toList();
    }
    @Override @Transactional(readOnly = true) public List<EmployeeResponse> getSalariesByEmployeeId(Long employeeId) {
        return assignmentRepository.findByEmployeeId(employeeId).stream().map(this::map).toList();
    }
    @Override public void deleteSalary(Long id) { assignmentRepository.delete(assignment(id)); }

    private Employee employee(Long id) {
        if (id == null) throw new IllegalArgumentException("employeeId is required; salary is assigned to an existing employee");
        return employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));
    }
    private SalaryComponent component(EmployeeRequest request) {
        if (request.getSalaryComponentId() != null)
            return componentRepository.findById(request.getSalaryComponentId())
                    .orElseThrow(() -> new IllegalArgumentException("Salary component not found: " + request.getSalaryComponentId()));
        if (request.getComponentName() == null || request.getComponentName().isBlank())
            throw new IllegalArgumentException("salaryComponentId or componentName is required");
        return componentRepository.findByComponentName(request.getComponentName()).orElseGet(() -> {
            SalaryComponent created = new SalaryComponent();
            created.setComponentName(request.getComponentName());
            created.setComponentType(request.getComponentType() == null ? "BASIC" : request.getComponentType());
            created.setActive(true);
            return componentRepository.save(created);
        });
    }
    private EmployeeSalaryComponent assignment(Long id) {
        return assignmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Salary assignment not found: " + id));
    }
    private EmployeeResponse map(EmployeeSalaryComponent value) {
        Employee employee = value.getEmployee();
        EmployeeResponse r = new EmployeeResponse();
        r.setId(value.getId()); r.setFirstName(employee.getFirstName()); r.setLastName(employee.getLastName());
        r.setEmail(employee.getEmail()); r.setPhone(employee.getPhone()); r.setDepartment(employee.getDepartment());
        r.setDesignation(employee.getDesignation()); r.setSalary(value.getAmount()); r.setJoiningDate(employee.getDateOfJoining());
        r.setEmployeeId(employee.getId()); r.setRelatedTypeId(value.getSalaryComponent().getId()); r.setAmount(value.getAmount());
        return r;
    }
}

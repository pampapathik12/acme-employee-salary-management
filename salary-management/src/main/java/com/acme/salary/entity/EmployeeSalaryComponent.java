package com.acme.salary.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employee_salary_components")
public class EmployeeSalaryComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_component_id", nullable = false)
    private SalaryComponent salaryComponent;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    public EmployeeSalaryComponent() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public SalaryComponent getSalaryComponent() { return salaryComponent; }
    public void setSalaryComponent(SalaryComponent salaryComponent) { this.salaryComponent = salaryComponent; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}

package com.acme.salary.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payroll_items")
public class PayrollItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_id", nullable = false)
    private Payroll payroll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_component_id", nullable = false)
    private SalaryComponent salaryComponent;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    public PayrollItem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Payroll getPayroll() { return payroll; }
    public void setPayroll(Payroll payroll) { this.payroll = payroll; }

    public SalaryComponent getSalaryComponent() { return salaryComponent; }
    public void setSalaryComponent(SalaryComponent salaryComponent) { this.salaryComponent = salaryComponent; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}

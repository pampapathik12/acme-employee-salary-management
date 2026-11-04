package com.acme.salary.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "leave_types")
@EntityListeners(AuditingEntityListener.class)
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String leaveName;

    @Column(precision = 5, scale = 2)
    private java.math.BigDecimal annualLimit;

    @Column(length = 255)
    private String description;

    public LeaveType() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLeaveName() { return leaveName; }
    public void setLeaveName(String leaveName) { this.leaveName = leaveName; }

    public java.math.BigDecimal getAnnualLimit() { return annualLimit; }
    public void setAnnualLimit(java.math.BigDecimal annualLimit) { this.annualLimit = annualLimit; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

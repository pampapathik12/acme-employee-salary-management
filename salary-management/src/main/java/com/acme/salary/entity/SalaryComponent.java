package com.acme.salary.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "salary_components")
public class SalaryComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String componentName;

    @Column(length = 20)
    private String componentType;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    public SalaryComponent() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getComponentName() { return componentName; }
    public void setComponentName(String componentName) { this.componentName = componentName; }

    public String getComponentType() { return componentType; }
    public void setComponentType(String componentType) { this.componentType = componentType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}

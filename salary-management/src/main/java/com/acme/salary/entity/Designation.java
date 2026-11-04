
package com.acme.salary.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "designations")
@EntityListeners(AuditingEntityListener.class)
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String designationName;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    public Designation() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDesignationName() { return designationName; }
    public void setDesignationName(String designationName) { this.designationName = designationName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}

package com.acme.salary.repository;

import com.acme.salary.entity.EmployeeSalaryComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSalaryComponentRepository
        extends JpaRepository<EmployeeSalaryComponent, Long> {

    List<EmployeeSalaryComponent> findByEmployeeId(Long employeeId);

    List<EmployeeSalaryComponent> findBySalaryComponentId(Long salaryComponentId);
}

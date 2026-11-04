package com.acme.salary.repository;

import com.acme.salary.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByEmployeeId(Long employeeId);

    List<Payroll> findByPayrollPeriodId(Long payrollPeriodId);

    List<Payroll> findByEmployeeIdAndPayrollPeriodId(
            Long employeeId,
            Long payrollPeriodId
    );
}

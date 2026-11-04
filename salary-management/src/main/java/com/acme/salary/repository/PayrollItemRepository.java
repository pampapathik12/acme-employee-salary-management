package com.acme.salary.repository;

import com.acme.salary.entity.PayrollItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollItemRepository extends JpaRepository<PayrollItem, Long> {

    List<PayrollItem> findByPayrollId(Long payrollId);

    List<PayrollItem> findBySalaryComponentId(Long salaryComponentId);
}

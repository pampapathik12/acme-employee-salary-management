package com.acme.salary.repository;

import com.acme.salary.entity.PayrollPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollPeriodRepository extends JpaRepository<PayrollPeriod, Long> {

    List<PayrollPeriod> findByStatus(String status);
}

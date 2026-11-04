package com.acme.salary.repository;

import com.acme.salary.entity.SalaryComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryComponentRepository extends JpaRepository<SalaryComponent, Long> {

    Optional<SalaryComponent> findByComponentName(String componentName);

    boolean existsByComponentName(String componentName);
}

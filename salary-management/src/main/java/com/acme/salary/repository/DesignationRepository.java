package com.acme.salary.repository;

import com.acme.salary.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

    Optional<Designation> findByDesignationName(String designationName);

    boolean existsByDesignationName(String designationName);
}

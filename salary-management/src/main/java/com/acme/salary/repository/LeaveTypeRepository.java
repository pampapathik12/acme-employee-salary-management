package com.acme.salary.repository;

import com.acme.salary.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {

    Optional<LeaveType> findByLeaveName(String leaveName);

    boolean existsByLeaveName(String leaveName);
}

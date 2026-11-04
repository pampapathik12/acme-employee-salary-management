package com.acme.salary.service.impl;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.entity.Department;
import com.acme.salary.repository.DepartmentRepository;
import com.acme.salary.service.DepartmentService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public EmployeeResponse createDepartment(EmployeeRequest request) {

        Department department = new Department();

        department.setDepartmentName(request.getDepartment());
        department.setDescription(request.getDesignation());

        Department savedDepartment = departmentRepository.save(department);

        return mapToResponse(savedDepartment);
    }

    @Override
    public EmployeeResponse updateDepartment(Long id, EmployeeRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Department not found with ID: " + id));

        department.setDepartmentName(request.getDepartment());
        department.setDescription(request.getDesignation());

        Department updatedDepartment = departmentRepository.save(department);

        return mapToResponse(updatedDepartment);
    }

    @Override
    public EmployeeResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Department not found with ID: " + id));

        return mapToResponse(department);
    }

    @Override
    public List<EmployeeResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Department not found with ID: " + id));

        departmentRepository.delete(department);
    }

    private EmployeeResponse mapToResponse(Department department) {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(department.getId());
        response.setDepartment(department.getDepartmentName());

        return response;
    }
}
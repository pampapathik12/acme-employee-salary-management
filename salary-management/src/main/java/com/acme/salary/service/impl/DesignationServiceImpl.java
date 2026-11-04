package com.acme.salary.service.impl;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.entity.Designation;
import com.acme.salary.repository.DesignationRepository;
import com.acme.salary.service.DesignationService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;

    public DesignationServiceImpl(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    @Override
    public EmployeeResponse createDesignation(EmployeeRequest request) {

        Designation designation = new Designation();

        designation.setDesignationName(request.getDesignation());
        designation.setActive(true);

        Designation savedDesignation = designationRepository.save(designation);

        return mapToResponse(savedDesignation);
    }

    @Override
    public EmployeeResponse updateDesignation(Long id, EmployeeRequest request) {

        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Designation not found with ID: " + id));

        designation.setDesignationName(request.getDesignation());

        Designation updatedDesignation = designationRepository.save(designation);

        return mapToResponse(updatedDesignation);
    }

    @Override
    public EmployeeResponse getDesignationById(Long id) {

        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Designation not found with ID: " + id));

        return mapToResponse(designation);
    }

    @Override
    public List<EmployeeResponse> getAllDesignations() {

        return designationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteDesignation(Long id) {

        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Designation not found with ID: " + id));

        designationRepository.delete(designation);
    }

    private EmployeeResponse mapToResponse(Designation designation) {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(designation.getId());
        response.setDesignation(designation.getDesignationName());

        return response;
    }
}
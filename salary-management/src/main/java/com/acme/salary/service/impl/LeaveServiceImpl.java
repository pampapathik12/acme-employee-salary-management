package com.acme.salary.service.impl;

import com.acme.salary.dto.EmployeeRequest;
import com.acme.salary.dto.EmployeeResponse;
import com.acme.salary.entity.Employee;
import com.acme.salary.entity.LeaveRequest;
import com.acme.salary.repository.EmployeeRepository;
import com.acme.salary.repository.LeaveRequestRepository;
import com.acme.salary.repository.LeaveTypeRepository;
import com.acme.salary.service.LeaveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRequestRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveServiceImpl(LeaveRequestRepository leaveRepository, EmployeeRepository employeeRepository,
                            LeaveTypeRepository leaveTypeRepository) {
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
        this.leaveTypeRepository = leaveTypeRepository;
    }

    @Override public EmployeeResponse applyLeave(EmployeeRequest request) {
        LeaveRequest leave = new LeaveRequest(); apply(leave, request, true);
        return map(leaveRepository.save(leave));
    }
    @Override public EmployeeResponse updateLeave(Long id, EmployeeRequest request) {
        LeaveRequest leave = find(id); apply(leave, request, false);
        return map(leaveRepository.save(leave));
    }
    @Override @Transactional(readOnly = true) public EmployeeResponse getLeaveById(Long id) { return map(find(id)); }
    @Override @Transactional(readOnly = true) public List<EmployeeResponse> getAllLeaves() {
        return leaveRepository.findAll().stream().map(this::map).toList();
    }
    @Override @Transactional(readOnly = true) public List<EmployeeResponse> getLeavesByEmployeeId(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId).stream().map(this::map).toList();
    }
    @Override public EmployeeResponse approveLeave(Long id, Long approvedBy) { return decide(id, approvedBy, "APPROVED"); }
    @Override public EmployeeResponse rejectLeave(Long id, Long approvedBy) { return decide(id, approvedBy, "REJECTED"); }
    @Override public void deleteLeave(Long id) { leaveRepository.delete(find(id)); }

    private EmployeeResponse decide(Long id, Long approverId, String status) {
        LeaveRequest leave = find(id);
        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() -> new IllegalArgumentException("Approver employee not found: " + approverId));
        leave.setApprovedBy(approver); leave.setStatus(status);
        return map(leaveRepository.save(leave));
    }
    private LeaveRequest find(Long id) {
        return leaveRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Leave request not found: " + id));
    }
    private void apply(LeaveRequest leave, EmployeeRequest request, boolean creating) {
        if (request.getEmployeeId() != null) leave.setEmployee(employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + request.getEmployeeId())));
        else if (creating) throw new IllegalArgumentException("employeeId is required");
        if (request.getLeaveTypeId() != null) leave.setLeaveType(leaveTypeRepository.findById(request.getLeaveTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Leave type not found: " + request.getLeaveTypeId())));
        else if (request.getLeaveType() != null && !request.getLeaveType().isBlank()) {
            leave.setLeaveType(leaveTypeRepository.findByLeaveName(request.getLeaveType()).orElseGet(() -> {
                com.acme.salary.entity.LeaveType type = new com.acme.salary.entity.LeaveType();
                type.setLeaveName(request.getLeaveType()); return leaveTypeRepository.save(type);
            }));
        } else if (creating) throw new IllegalArgumentException("leaveTypeId or leaveType is required");
        if (request.getStartDate() != null) leave.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) leave.setEndDate(request.getEndDate());
        if (request.getReason() != null) leave.setReason(request.getReason());
        if (creating && (leave.getStartDate() == null || leave.getEndDate() == null))
            throw new IllegalArgumentException("startDate and endDate are required");
        if (leave.getStartDate() != null && leave.getEndDate() != null && leave.getEndDate().isBefore(leave.getStartDate()))
            throw new IllegalArgumentException("endDate must be on or after startDate");
    }
    private EmployeeResponse map(LeaveRequest leave) {
        EmployeeResponse r = new EmployeeResponse(); Employee employee = leave.getEmployee();
        r.setId(leave.getId()); r.setEmployeeId(employee.getId()); r.setFirstName(employee.getFirstName());
        r.setLastName(employee.getLastName()); r.setEmail(employee.getEmail()); r.setRelatedTypeId(leave.getLeaveType().getId());
        r.setStartDate(leave.getStartDate()); r.setEndDate(leave.getEndDate()); r.setReason(leave.getReason()); r.setStatus(leave.getStatus());
        return r;
    }
}

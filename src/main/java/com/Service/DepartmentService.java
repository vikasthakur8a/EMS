package com.Service;

import org.springframework.stereotype.Service;

import com.entity.Department;
import com.repository.DepartmentRepository;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId).orElse(null);
    }

    public Department getDepartmentWithLeastEmployees() {
        return departmentRepository.findDepartmentWithLeastEmployees();
    }
}
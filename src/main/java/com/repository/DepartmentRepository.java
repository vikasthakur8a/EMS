package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.Department;
import com.entity.Employee;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Employee> findByEmployeesDepartments(Department department);
    
    @Query(value="SELECT d FROM Department d LEFT JOIN d.employees e GROUP BY d.id ORDER BY COUNT(e) ASC LIMIT 1")
    Department findDepartmentWithLeastEmployees();
}

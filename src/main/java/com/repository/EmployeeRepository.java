package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.Department;
import com.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT e FROM Employee e INNER JOIN e.departments d GROUP BY d.id HAVING COUNT(e) = (SELECT MIN(COUNT(e2)) FROM Employee e2 INNER JOIN e2.departments d2 GROUP BY d2.id)")
	List<Employee> findEmployeesWithLeastEmployeesByDepartment();

	List<Employee> findByDepartments(Department department);

	@Query(value = "SELECT e FROM Employee e WHERE SIZE(e.departments) > 1")
	List<Employee> findEmployeesWorkingForMultipleDepartments();

	@Query("SELECT DISTINCT e.departments FROM Employee e")
	List<Department> findDistinctDepartments();

	@Query("SELECT DISTINCT e FROM Employee e INNER JOIN FETCH e.departments d WHERE d = :department AND e.salary = (SELECT MAX(e2.salary) FROM Employee e2 INNER JOIN e2.departments d2 WHERE d2 = :department)")
	List<Employee> findByDepartmentsAndHighestSalary(@Param("department") Department department);
}

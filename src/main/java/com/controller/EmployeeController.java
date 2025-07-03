package com.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Service.DepartmentService;
import com.Service.EmployeeService;
import com.entity.Department;
import com.entity.Employee;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	private final EmployeeService employeeService;
	private final DepartmentService departmentService;

	public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
		this.employeeService = employeeService;
		this.departmentService = departmentService;
	}

	
	@GetMapping("/highest-salary")
	public ResponseEntity<List<Employee>> getEmployeesWithHighestSalaryByDepartment() {
		List<Employee> employees = employeeService.getEmployeesWithHighestSalaryByDepartment();
		return ResponseEntity.ok(employees);
			
	}
	@GetMapping("/department/{departmentId}")
	public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable Long departmentId) {
		Department department = departmentService.getDepartmentById(departmentId);
		if (department == null) {
			return ResponseEntity.notFound().build();
		}
		List<Employee> employees = employeeService.getEmployeesByDepartment(department);
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/least-employees")
	public ResponseEntity<Department> getDepartmentWithLeastEmployees() {
		Department department = departmentService.getDepartmentWithLeastEmployees();
		if (department == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(department);
	}

	@GetMapping("/multiple-departments")
	public ResponseEntity<List<Employee>> getEmployeesWorkingForMultipleDepartments() {
		List<Employee> employees = employeeService.getEmployeesWorkingForMultipleDepartments();
		return ResponseEntity.ok(employees);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
		employeeService.addEmployee(employee);
		return ResponseEntity.ok("Employee added successfully.");
	}

	@DeleteMapping("/{employeeId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId) {
		employeeService.deleteEmployee(employeeId);
		return ResponseEntity.ok("Deleted Successfully");
	}
}
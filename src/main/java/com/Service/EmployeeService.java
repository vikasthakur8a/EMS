package com.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.entity.Department;
import com.entity.Employee;
import com.repository.DepartmentRepository;
import com.repository.EmployeeRepository;

@Service
public class EmployeeService {
	private final EmployeeRepository employeeRepository;

	private final DepartmentRepository departmentRepository;

	public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
	}

	public List<Employee> getEmployeesWithHighestSalaryByDepartment() {
		List<Employee> employees = new ArrayList<>();
		List<Department> departments = employeeRepository.findDistinctDepartments();

		for (Department department : departments) {
			List<Employee> employeesWithHighestSalary = employeeRepository
					.findByDepartmentsAndHighestSalary(department);
			employeesWithHighestSalary.stream()
					.filter(employee -> !employees.stream().anyMatch(e -> e.getId().equals(employee.getId())))
					.forEach(employees::add);
		}

		return employees;
	}

	public List<Employee> getEmployeesByDepartment(Department department) {
		return employeeRepository.findByDepartments(department);
	}

	public List<Employee> getEmployeesWorkingForMultipleDepartments() {
		return employeeRepository.findAll().stream().filter(employee -> employee.getDepartments().size() > 1)
				.collect(Collectors.toList());
	}

	public Employee addEmployee(Employee employee) {
		for (Department department : employee.getDepartments()) {
			if (department.getId() == null) {
				Department savedDepartment = departmentRepository.save(department);
				department.setId(savedDepartment.getId());
			}
			department.getEmployees().add(employee);
		}
		return employeeRepository.save(employee);
	}

	public void deleteEmployee(Long employeeId) {
		employeeRepository.deleteById(employeeId);
	}
}
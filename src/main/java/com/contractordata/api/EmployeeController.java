package com.contractordata.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contractordata.model.Employee;
import com.contractordata.service.EmployeeService;

@RequestMapping("api/v1/employee")
@RestController
public class EmployeeController {

	private final EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@PostMapping
	public void addPerson(@Valid @NotNull @RequestBody Employee employee) {
		employeeService.addEmployee(employee);
	}
	
	@GetMapping
	public List<Employee> getEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@GetMapping(path = "{id}")
	public Employee getEmployeeById(@PathVariable("id") UUID id) {
		return employeeService.getEmployeeById(id)
				.orElse(null);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteEmployeeById(@PathVariable("id") UUID id) {
		employeeService.deleteEmployee(id);
	}
	
	@PutMapping(path = "{id}")
	public void updateEmployee(@Valid @NotNull @PathVariable("id") UUID id,
			@RequestBody Employee employeeToUpdate) {
		employeeService.updateEmployee(id, employeeToUpdate);
	}
	
}

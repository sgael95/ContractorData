package com.contractordata.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.contractordata.model.Employee;

public interface EmployeeDao {
	
	int insertEmployee(UUID employeeId, Employee employee);

	default int insertEmplyee(Employee employee) {
		UUID id = UUID.randomUUID();
		return insertEmployee(id, employee);
	}
	
	List<Employee> selectAllEmployees();
	
	Optional<Employee> selectEmployeeById(UUID id);
	
	int deleteEmployeeById(UUID id);
	
	int updateEmployeeById(UUID id, Employee employee);
}

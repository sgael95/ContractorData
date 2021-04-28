package com.contractordata.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.contractordata.dao.EmployeeDao;
import com.contractordata.model.Employee;

@Service
public class EmployeeService {

	private final EmployeeDao employeeDao;
	
	@Autowired
	public EmployeeService(@Qualifier("postgres") EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	public int addEmployee(Employee employee) {
		return employeeDao.insertEmplyee(employee);
	}
	
	public List<Employee> getAllEmployees() {
		return employeeDao.selectAllEmployees();
	}
	
	public Optional<Employee> getEmployeeById(UUID id){
		return employeeDao.selectEmployeeById(id);
	}
	
	public int deleteEmployee(UUID id) {
		return employeeDao.deleteEmployeeById(id);
	}
	
	public int updateEmployee(UUID id, Employee employeeToUpdate) {
		return employeeDao.updateEmployeeById(id, employeeToUpdate);
	}
}

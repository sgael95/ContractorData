package com.contractordata.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import static org.mockito.BDDMockito.given;
import com.contractordata.model.Employee;

@SuppressWarnings("deprecation")
public class EmployeeDataAccessServiceTest {

	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EmployeeDataAccessService underTest;
	
	private ResultSetExtractor<List<Employee>> rowMapper;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new EmployeeDataAccessService(jdbcTemplate);
	}
	
	
	@Test
	void itShouldInsertEmployee() {
		// Given
		UUID employeeId = UUID.randomUUID();
		String firstName = "TestName";
		String lastName = "TestLastName";
		String homeAddress = "2345 Home Ave";
		int phoneNumber = 123456789;
		String insertSql = "INSERT INTO employee (employee_uuid, first_name, last_name, home_address, phone_number) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		// Mock employee
		Employee employee = new Employee(employeeId, 
				firstName,
				lastName, 
				homeAddress,
				phoneNumber);
	
		List<Employee> employees = underTest.selectAllEmployees();

		given(jdbcTemplate.update(insertSql, any(Object.class)))
			.willReturn( employees.add(employee) ? 1 : 0 );
		
		// When
		underTest.insertEmployee(employeeId, employee);
		
		// Then 
		assertThat(employee).isEqualToComparingFieldByField(employees.get(0));
		
	}
	
	@Test
	void itShouldReturnAllEmployees() {
		// Given
		UUID employeeId = UUID.randomUUID();
		String firstName = "TestName";
		String lastName = "TestLastName";
		String homeAddress = "2345 Home Ave";
		int phoneNumber = 28492;
		
		String sql = "SELECT employee_uuid, first_name, last_name, home_address, phone_number" 
				+ " FROM employee";
		
		UUID employeeId2 = UUID.randomUUID();
		String firstName2 = "Gael";
		String lastName2 = "Sanchez";
		String homeAddress2 = "HomeAddress";
		int phoneNumber2 = 210210210;
		
		
		// Mock employees
		Employee employee = new Employee(employeeId, 
				firstName,
				lastName, 
				homeAddress,
				phoneNumber);
		
		Employee employee2 = new Employee(employeeId2,
				firstName2, 
				lastName2, 
				homeAddress2,
				phoneNumber2);
		
		// ... add employees to mock list
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(employee);
		employees.add(employee2);

		given(jdbcTemplate.query(sql, rowMapper)).willReturn(employees);
		
		// When
		List<Employee> testEmployees = underTest.selectAllEmployees();
		
		//Then
		testEmployees.forEach(e -> {
			int index = testEmployees.indexOf(e);
			assertThat(e).isEqualToComparingFieldByField(employees.get(index));
		});
	}
	
	@Test
	void itShouldSelectEmployeeById() {
		// Given
		UUID employeeId = UUID.randomUUID();
		String firstName = "TestName";
		String lastName = "TestLastName";
		String homeAddress = "2345 Home Ave";
		int phoneNumber = 28492;
		
		// Mock employees
		Employee employee = new Employee(employeeId, 
				firstName,
				lastName, 
				homeAddress,
				phoneNumber);

		given(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.<Object[]> anyVararg(), Matchers.any(RowMapper.class))).willReturn(employee);
		
		//when
		Optional<Employee> testEmployee = underTest.selectEmployeeById(employeeId);
		
		// Then
		assertThat(testEmployee).isNotEmpty();
		assertThat(testEmployee.get()).isEqualToComparingFieldByField(employee);
	}
	
	@Test
	void deleteEmployeeById() {
		// Given
		String sql = "DELETE FROM employee WHERE employee_uuid = ?";
		
		UUID employeeId = UUID.randomUUID();
		String firstName = "TestName";
		String lastName = "TestLastName";
		String homeAddress = "2345 Home Ave";
		int phoneNumber = 28492;
		
		// Mock employees
		Employee employee = new Employee(employeeId, 
				firstName,
				lastName, 
				homeAddress,
				phoneNumber);
		
		UUID employeeId2 = UUID.randomUUID();
		String firstName2 = "Gael";
		String lastName2 = "Sanchez";
		String homeAddress2 = "HomeAddress";
		int phoneNumber2 = 210210210;
		
		// Mock second employee
		Employee employee2 = new Employee(employeeId2,
				firstName2,
				lastName2,
				homeAddress2,
				phoneNumber2);
		
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(employee);
		employees.add(employee2);
		
		// When
		given(jdbcTemplate.update(sql, any(Object.class))).willReturn(1);
		
		employees.remove(1);
		
		// Then
		
		assertThat(employees).isNotNull();
		assertThat(employees).size().isEqualTo(1);
		assertThat(employees.get(0)).isEqualToComparingFieldByField(employee);
	}
	
	@Test
	void itShouldUpdateEmployeeById() {
		// Given
		String sql = "UPDATE employee SET first_name = ?, last_name = ?, home_address = ?,"
				+ " phone_number = ? WHERE employee_uuid = ?";
		
		UUID employeeId = UUID.randomUUID();
		String firstName = "TestName";
		String lastName = "TestLastName";
		String homeAddress = "2345 Home Ave";
		int phoneNumber = 28492;
		
		// Mock employees
		Employee employee = new Employee(employeeId, 
				firstName,
				lastName, 
				homeAddress,
				phoneNumber);
		
		// When
		given(jdbcTemplate.update(sql, any(Object.class))).willReturn(1);
		Employee updatedEmployee = new Employee(employeeId,
				"Jaime",
				lastName,
				homeAddress,
				phoneNumber);
		employee = updatedEmployee;
		
		// Then
		assertThat(employee).isEqualToComparingFieldByField(updatedEmployee);
	}
}

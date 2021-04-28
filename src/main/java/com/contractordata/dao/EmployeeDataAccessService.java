package com.contractordata.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.contractordata.model.Employee;

@Repository("postgres")
public class EmployeeDataAccessService implements EmployeeDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public EmployeeDataAccessService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertEmployee(UUID employeeId, Employee employee) {
		final String sql = "INSERT INTO employee (employee_uuid, first_name, last_name, home_address, phone_number) "
			+ "VALUES (?, ?, ?, ?, ?)";
		String firstName = employee.getFirstName();
		String lastName = employee.getLastName();
		String homeAddress = employee.getAddress();
		String phoneNumber = "";
		phoneNumber = Integer.toString(employee.getPhoneNumber());
		
		return jdbcTemplate.update(sql, new Object[] {employeeId, firstName, lastName, homeAddress, phoneNumber});
	}

	@Override
	public List<Employee> selectAllEmployees() {

		final String sql = "SELECT employee_uuid, first_name, last_name, home_address, phone_number" + " FROM employee";
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			UUID id = UUID.fromString(resultSet.getString("employee_uuid"));
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String homeAddress = resultSet.getString("home_address");
			int phoneNumber = Integer.parseInt(resultSet.getString("phone_number"));
				
			return new Employee(id, firstName, lastName, homeAddress, phoneNumber);
		});
	}

	@Override
	public Optional<Employee> selectEmployeeById(UUID id) {

		final String sql = "SELECT employee_uuid, first_name, last_name, home_address, phone_number"
				+ " FROM employee WHERE employee_uuid = ?";
		@SuppressWarnings("deprecation")
		Employee employee = jdbcTemplate.queryForObject(sql, new Object[] { id }, (resultSet, i) -> {
			UUID employeeId = UUID.fromString(resultSet.getString("employee_uuid"));
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String homeAddress = resultSet.getString("home_address");
			int phoneNumber = Integer.parseInt(resultSet.getString("phone_number"));
			
			return new Employee(employeeId, firstName, lastName, homeAddress, phoneNumber);
		});
		return Optional.ofNullable(employee);
	}

	@Override
	public int deleteEmployeeById(UUID id) {
		final String sql = "DELETE FROM employee WHERE employee_uuid = ?";
		int deletedEmployee = jdbcTemplate.update(sql, new Object[] { id });
		return deletedEmployee;
	}

	@Override
	public int updateEmployeeById(UUID id, Employee employee) {
		final String sql = "UPDATE employee SET first_name = ?, last_name = ?, home_address = ?,"
				+ " phone_number = ? WHERE employee_uuid = ?";
		String firstName = employee.getFirstName();
		String lastName = employee.getLastName();
		String address = employee.getAddress();
		String phoneNumber = Integer.toString(employee.getPhoneNumber());
		
		return jdbcTemplate.update(sql, new Object[] {firstName, lastName, address, phoneNumber, id});
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}

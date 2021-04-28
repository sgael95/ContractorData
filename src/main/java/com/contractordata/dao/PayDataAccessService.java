package com.contractordata.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.contractordata.model.Pay;

@Repository("payPostgres")
public class PayDataAccessService implements PayDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public PayDataAccessService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertPayment(UUID payId, Pay pay) {
		final String sql = "INSERT INTO pay (pay_uuid, house_uuid, employee_uuid, amount_paid) "
			+ "VALUES (?, ?, ?, ?)";
		UUID houseId = pay.getHouseId();
		UUID employeeId = pay.getEmployeeId();
		double amountPaid = pay.getAmountPaid();
		
		return jdbcTemplate.update(sql, new Object[] {payId, houseId, employeeId, amountPaid});
	}

	@Override
	public List<Pay> selectAllPayments() {
		final String sql = "SELECT pay_uuid, house_uuid, employee_uuid, amount_paid "
				+ "FROM pay";
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			UUID id = UUID.fromString(resultSet.getString("pay_uuid"));
			UUID houseId = UUID.fromString(resultSet.getString("house_uuid"));
			UUID employeeId = UUID.fromString(resultSet.getString("employee_uuid"));
			double amountPaid = Double.parseDouble(resultSet.getString("amount_paid"));
			
			return new Pay(id, houseId, employeeId, amountPaid);
		});
	}

	@Override
	public Optional<Pay> selectPaymentById(UUID id) {
		final String sql = "SELECT pay_uuid, house_uuid, employee_uuid, amount_paid "
				+ "FROM pay WHERE pay_uuid = ?";
		
		@SuppressWarnings("deprecation")
		Pay pay = jdbcTemplate.queryForObject(sql, new Object[] { id }, (resultSet, i) -> {
			UUID payId = UUID.fromString(resultSet.getString("pay_uuid"));
			UUID houseId = UUID.fromString(resultSet.getString("house_uuid"));
			UUID employeeId = UUID.fromString(resultSet.getString("employee_uuid"));
			double amountPaid = Double.parseDouble(resultSet.getString("amount_paid"));
			
			return new Pay(payId, houseId, employeeId, amountPaid);
		});
		return Optional.ofNullable(pay);
	}

	@Override
	public int deletePayment(UUID id) {
		final String sql = "DELETE FROM pay WHERE pay_uuid = ?";
		int deletePayment = jdbcTemplate.update(sql, new Object[] { id });
		return deletePayment;
	}

	@Override
	public int updatePayment(UUID id, Pay pay) {
		final String sql = "UPDATE pay SET house_uuid = ?, employee_uuid = ?, amount_paid = ?,"
				+ " WHERE pay_uuid = ?";
		UUID houseId = pay.getHouseId();
		UUID employeeId = pay.getEmployeeId();
		double amountPaid = pay.getAmountPaid();
		
		return jdbcTemplate.update(sql, new Object[] {houseId, employeeId, amountPaid, id});

	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
}

package com.contractordata.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.contractordata.model.House;

@Repository("housePostgres")
public class HouseDataAccessService implements HouseDao {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public HouseDataAccessService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertHouse(UUID houseId, House house) {
		final String sql = "INSERT INTO house (house_uuid, address, size, amount_paid) "
				+ "VALUES (?, ?, ?, ?)";
		String address = house.getAddress();
		int size = house.getSize();
		double amountPaid = house.getAmountPaid();
		
		return jdbcTemplate.update(sql, new Object[] {houseId, address, size, amountPaid});
	}

	@Override
	public List<House> selectAllHouses() {
		final String sql = "SELECT house_uuid, address, size, amount_paid " 
				+ "FROM house";
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			UUID id = UUID.fromString(resultSet.getString("house_uuid"));
			String address = resultSet.getString("address");
			int size = Integer.parseInt(resultSet.getString("size"));
			double amountPaid = Double.parseDouble(resultSet.getString("amount_paid"));
			
			return new House(id, address, size, amountPaid);
		});
	}

	@Override
	public Optional<House> selectHouseById(UUID id) {
		final String sql = "SELECT house_uuid, address, size, amount_paid " 
				+ "FROM house WHERE house_uuid = ?";
		
		@SuppressWarnings("deprecation")
		House house = jdbcTemplate.queryForObject(sql, new Object[] { id },	(resultSet, i) -> {
			UUID houseId = UUID.fromString(resultSet.getString("house_uuid"));
			String address = resultSet.getString("address");
			int size = Integer.parseInt(resultSet.getString("size"));
			double amountPaid = Double.parseDouble(resultSet.getString("amount_paid"));
			
			return new House(houseId, address, size, amountPaid);
		});
		return Optional.ofNullable(house);
	}

	@Override
	public int deleteHouseById(UUID id) {
		final String sql = "DELETE FROM house WHERE house_uuid = ?";
		int deleteHouse = jdbcTemplate.update(sql, new Object[] { id });
		return deleteHouse;
	}

	@Override
	public int updateHouseById(UUID id, House house) {
		final String sql = "UPDATE house SET address = ?, size = ?, amount_paid = ?"
				+ " WHERE house_uuid = ?";
		String address = house.getAddress();
		int size = house.getSize();
		double amountPaid = house.getAmountPaid();
		
		return jdbcTemplate.update(sql, new Object[] { address, size, amountPaid, id});
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}

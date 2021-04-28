package com.contractordata.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

import com.contractordata.model.House;

class HouseDataAccessServiceTest {
	
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private HouseDataAccessService underTest;
	
	private ResultSetExtractor<List<House>> rowMapper;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		underTest = new HouseDataAccessService(jdbcTemplate);
	}

	@Test
	void itShouldInsertHouse() {
		// Given
		String sql = "INSERT INTO house (house_uuid, address, size, amount_paid) "
				+ "VALUES (?, ?, ?, ?)";
		
		UUID houseId = UUID.randomUUID();
		String address = "123 This Way";
		int size = 1500;
		double amountPaid = 700.0;
		
		House house = new House(houseId,
				address,
				size,
				amountPaid);
		
		List<House> houses = underTest.selectAllHouses();
		
		given(jdbcTemplate.update(sql, any(Object.class)))
			.willReturn(1);
		
		houses.add(house);
		
		// When
		underTest.insertHouse(houseId, house);
		
		// Then
		assertThat(house).isEqualToComparingFieldByField(houses.get(0));
	}

	@Test
	void itShouldReturnAllHouses() {
		// Given
		String sql = "SELECT house_uuid, address, size, amount_paid"
				+ "FROM house";
		
		// Mocking houses
		UUID houseId = UUID.randomUUID();
		String address = "123 This Way";
		int size = 1500;
		double amountPaid = 700.0;
		
		House house = new House(houseId,
				address,
				size,
				amountPaid);
		
		UUID houseId2 = UUID.randomUUID();
		String address2 = "1234 This Way";
		int size2 = 1501;
		double amountPaid2 = 700.5;
		
		House house2 = new House(houseId,
				address,
				size,
				amountPaid);
		
		// ... Creating fake database
		List<House> houses = new ArrayList<House>();
		houses.add(house);
		houses.add(house2);
		
		given(jdbcTemplate.query(sql, rowMapper)).willReturn(houses);
		
		// When 
		List<House> testHouses = underTest.selectAllHouses();
		
		// Then
		testHouses.forEach(h -> {
			int index = testHouses.indexOf(h);
			assertThat(h).isEqualToComparingFieldByField(houses.get(index));
		});
	}
	
	@Test
	void itShouldSelectHouseById() {
		// Given
		UUID houseId = UUID.randomUUID();
		String address = "123 This Way";
		int size = 1500;
		double amountPaid = 700.0;
		
		House house = new House(houseId,
				address,
				size,
				amountPaid);
		given(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.<Object[]> anyVararg(), Matchers.any(RowMapper.class))).willReturn(house);

		// When
		Optional<House> testHouse = underTest.selectHouseById(houseId);
		
		// Then
		assertThat(testHouse).isNotEmpty();
		assertThat(testHouse.get()).isEqualToComparingFieldByField(house);
	}
	
	@Test
	void deleteHouseById() {
		// Given
		String sql = "DELETE FROM house WHERE house_uuid = ?";
		
		UUID houseId = UUID.randomUUID();
		String address = "123 This Way";
		int size = 1500;
		double amountPaid = 700.0;
		
		House house = new House(houseId,
				address,
				size,
				amountPaid);
		
		UUID secondHouseId = UUID.randomUUID();
		String secondAddress = "123 This Way";
		int secondSize = 1500;
		double secondAmountPaid = 700.0;
		
		House secondHouse = new House(secondHouseId,
				secondAddress, secondSize, secondAmountPaid);
		
		List<House> houses = new ArrayList<House>();
		houses.add(house);
		houses.add(secondHouse);
		
		// When
		given(jdbcTemplate.update(sql, any(Object.class))).willReturn(1);		
		houses.remove(1);
		
		// Then
		
		assertThat(houses).isNotNull();
		assertThat(houses).size().isEqualTo(1);
		assertThat(houses.get(0)).isEqualToComparingFieldByField(house);
	}
	
	@Test
	void itShouldUpdateEmployeeById() {
		// Given
		String sql = "UPDATE house SET address = ?, size = ?, amount_paid = ?" 
				+ " WHERE house_uuid = ?";
		
		UUID houseId = UUID.randomUUID();
		String address = "123 This Way";
		int size = 1500;
		double amountPaid = 700.0;
		
		House house = new House(houseId,
				address,
				size,
				amountPaid);
		
		// When
		given(jdbcTemplate.update(sql, any(Object.class))).willReturn(1);
		House updatedHouse = new House(houseId,
				"1212 That Way",
				size,
				amountPaid);
		house = updatedHouse;
		
		// Then
		assertThat(house).isEqualToComparingFieldByField(updatedHouse);
	}
}

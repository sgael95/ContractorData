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

import com.contractordata.model.House;
import com.contractordata.service.HouseService;

@RequestMapping("api/v1/house")
@RestController
public class HouseController {
	
	private final HouseService houseService;
	
	@Autowired
	public HouseController(HouseService houseService) {
		this.houseService = houseService;
	}

	@PostMapping
	public void addHouse(@Valid @NotNull @RequestBody House house) {
		houseService.addHouse(house);
	}
	
	@GetMapping
	public List<House> getHouses() {
		return houseService.getAllHouses();
	}
	
	@GetMapping(path = "{id}")
	public House getHouseById(@PathVariable("id") UUID id) {
		return houseService.getHouseById(id)
				.orElse(null);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteHouseById(@PathVariable("id") UUID id) {
		houseService.deleteHouse(id);
	}
	
	@PutMapping(path = "{id}")
	public void updateHouse(@Valid @NotNull @PathVariable("id") UUID id,
			@RequestBody House houseToUpdate) {
		houseService.updateHouse(id, houseToUpdate);
	}
}
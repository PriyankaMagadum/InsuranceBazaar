package com.synechron.insurancebazaar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.insurancebazaar.config.Response;
import com.synechron.insurancebazaar.modal.City;
import com.synechron.insurancebazaar.service.CityService;

@RestController
public class CityController {

	@Autowired
	private CityService cityService;

	@GetMapping("/city")
	public ResponseEntity<Response> getAllCity() {

		List<City> cities = cityService.getAllCity();
		String message = "Success";
		if (cities.size() == 0) {
			message = "Cities does not exists.";
		}
		return new ResponseEntity<Response>(new Response(message, cities), HttpStatus.OK);

	}

}

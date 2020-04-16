package com.synechron.insurancebazaar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.insurancebazaar.modal.City;
import com.synechron.insurancebazaar.repository.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public List<City> getAllCity() {
		return cityRepository.findAll();
	}

	public City getCityById(long id) {
		Optional<City> cityOptional = cityRepository.findById(id);
		return cityOptional.isPresent() ? cityOptional.get() : null;
	}

}

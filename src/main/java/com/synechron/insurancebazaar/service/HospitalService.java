package com.synechron.insurancebazaar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.insurancebazaar.exception.ObjectNotFoundException;
import com.synechron.insurancebazaar.modal.City;
import com.synechron.insurancebazaar.modal.Hospital;
import com.synechron.insurancebazaar.repository.HospitalRepository;

@Service
public class HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;
//
//	@Autowired
//	private City city;

	@Autowired
	private CityService cityService;

	public List<Hospital> findByCity(long cityId) throws ObjectNotFoundException {

		City city = cityService.getCityById(cityId);

		validateForNull(city);

		return hospitalRepository.findByCity(city);
	}

	public Hospital saveHospital(Hospital hospital) throws ObjectNotFoundException {

		return hospitalRepository.saveAndFlush(hospital);
	}

	public Hospital findById(long id) {

		Optional<Hospital> hospital = hospitalRepository.findById(id);
		return hospital.isPresent() ? hospital.get() : null;
	}

	private void validateForNull(City city) throws ObjectNotFoundException {
		if (city == null) {
			throw new ObjectNotFoundException("City does not exists.");
		}
	}
}

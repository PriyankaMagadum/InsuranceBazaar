package com.synechron.insurancebazaar.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.synechron.insurancebazaar.config.Response;
import com.synechron.insurancebazaar.exception.ObjectNotFoundException;
import com.synechron.insurancebazaar.modal.City;
import com.synechron.insurancebazaar.modal.Hospital;
import com.synechron.insurancebazaar.service.CityService;
import com.synechron.insurancebazaar.service.HospitalService;

@RestController
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private CityService cityService;

	@Autowired
	private City city;

	@Autowired
	private Hospital hospital;

	@GetMapping("/hospital/city/{cityId}")
	public ResponseEntity<Response> getHospitalByCity(@PathVariable("cityId") long cityId)
			throws ObjectNotFoundException {

		List<Hospital> hospitals = hospitalService.findByCity(cityId);
		if (hospitals.size() == 0) {
			throw new ObjectNotFoundException("Hospitals does not exists.");
		}

		List<Resource<Hospital>> hospitalList = hospitals.stream().map(t -> {
			try {
				return getAllHospitalLinks(t, new Resource<Hospital>(t));
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}).filter(t -> t != null).collect(Collectors.toList());

		return new ResponseEntity<Response>(new Response("Success", hospitalList), HttpStatus.OK);
	}

	@PostMapping("/hospital")
	public ResponseEntity<Response> saveHospital(@RequestBody @Valid Hospital newHospital)
			throws ObjectNotFoundException {

		hospital = hospitalService.saveHospital(newHospital);

		if (hospital == null) {
			return new ResponseEntity<Response>(new Response("Failed, Please try again.", null),
					HttpStatus.BAD_REQUEST);
		}
		Resource resource = new Resource<Hospital>(hospital);

		return new ResponseEntity<Response>(new Response("Success", getAllHospitalLinks(hospital, resource)),
				HttpStatus.CREATED);
	}

	@GetMapping("/hospital/{id}")
	public ResponseEntity<Response> getHospitalById(@PathVariable("id") long id) throws ObjectNotFoundException {

		Hospital hospital = hospitalService.findById(id);

		if (hospital == null) {
			throw new ObjectNotFoundException("Hospital does not exists.");
		}

		return new ResponseEntity<Response>(
				new Response("Success", getAllHospitalLinks(hospital, new Resource<Hospital>(hospital))),
				HttpStatus.OK);
	}

	public Resource<Hospital> getAllHospitalLinks(Hospital hospital, Resource<Hospital> resource)
			throws ObjectNotFoundException {

		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getHospitalByCity(hospital.getCity().getId()))
				.withRel("hospital-by-city").withType("GET"));

		resource.add(
				ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).saveHospital(hospital))
						.withRel("save-hospital").withType("POST"));
		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getHospitalById(hospital.getId()))
				.withRel("hospital-by-id").withType("GET"));

		return resource;
	}

}

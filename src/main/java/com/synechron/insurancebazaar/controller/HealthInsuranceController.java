package com.synechron.insurancebazaar.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.insurancebazaar.config.Response;
import com.synechron.insurancebazaar.exception.InvalidUserException;
import com.synechron.insurancebazaar.exception.ObjectNotFoundException;
import com.synechron.insurancebazaar.modal.Feature;
import com.synechron.insurancebazaar.modal.HealthInsurancePlan;
import com.synechron.insurancebazaar.modal.Hospital;
import com.synechron.insurancebazaar.service.HealthInsurancePlanService;

@RestController
@RequestMapping("/healthInsurancePlan")
public class HealthInsuranceController {

	@Autowired
	private HealthInsurancePlanService healthInsurancePlanService;

	@GetMapping("/{id}")
	public ResponseEntity<Response> getHealthInsurancePlanById(@PathVariable("id") long id)
			throws ObjectNotFoundException, InvalidUserException {

		HealthInsurancePlan healthInsurancePlan = healthInsurancePlanService.getByIdAndIsActive(id, true);

		if (healthInsurancePlan == null) {
			throw new ObjectNotFoundException("Health Insurance Plan does not exists.");
		}

		return new ResponseEntity<Response>(new Response("Success", getAllHealthInsurancePlanLinks(healthInsurancePlan,
				new Resource<HealthInsurancePlan>(healthInsurancePlan))), HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<Response> getAllHealthInsurancePlan() throws ObjectNotFoundException {

		List<HealthInsurancePlan> healthInsurancePlans = healthInsurancePlanService.getByIsActive(true);

		if (healthInsurancePlans.size() == 0) {
			throw new ObjectNotFoundException("Health Insurance Plans does not exists.");
		}

		List<Resource<HealthInsurancePlan>> healthInsurancePlanList = healthInsurancePlans.stream().map(t -> {
			try {
				try {
					return getAllHealthInsurancePlanLinks(t, new Resource<HealthInsurancePlan>(t));
				} catch (InvalidUserException e) {
					e.printStackTrace();
					return null;
				}
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}).filter(t -> t != null).collect(Collectors.toList());

		return new ResponseEntity<Response>(new Response("Success", healthInsurancePlanList), HttpStatus.OK);

	}

	@GetMapping("/{id}/feature")
	public ResponseEntity<Response> getFeaturesOfHealthInsurancePlan(@PathVariable("id") long id)
			throws ObjectNotFoundException {
		HealthInsurancePlan healthInsurancePlan = healthInsurancePlanService.getByIdAndIsActive(id, true);
		if (healthInsurancePlan == null) {
			throw new ObjectNotFoundException("Health Insurance Plan for provided id does not exists.");
		}

		List<Resource<Feature>> featureList = healthInsurancePlan.getFeatures().stream().map(t -> {
			try {
				return new FeatureController().getAllFeatureLinks(t, new Resource<Feature>(t));
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}).filter(t -> t != null).collect(Collectors.toList());
		return new ResponseEntity<Response>(new Response("Success", featureList), HttpStatus.OK);

	}

	@GetMapping("/{id}/hospital")
	public ResponseEntity<Response> getHospitalsOfHealthInsurancePlan(@PathVariable("id") long id)
			throws ObjectNotFoundException {
		HealthInsurancePlan healthInsurancePlan = healthInsurancePlanService.getByIdAndIsActive(id, true);
		if (healthInsurancePlan == null) {
			throw new ObjectNotFoundException("Health Insurance Plan for provided id does not exists.");
		}

		List<Resource<Hospital>> hospitalList = healthInsurancePlan.getHospitals().stream().map(t -> {
			try {
				return new HospitalController().getAllHospitalLinks(t, new Resource<Hospital>(t));
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}).filter(t -> t != null).collect(Collectors.toList());
		return new ResponseEntity<Response>(new Response("Success", hospitalList), HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<Response> saveHealthInsurancePlan(
			@RequestBody @Valid HealthInsurancePlan newHealthInsurancePlan)
			throws ObjectNotFoundException, InvalidUserException {

		HealthInsurancePlan healthInsurancePlan = healthInsurancePlanService
				.saveHealthInsurancePlan(newHealthInsurancePlan);

		if (healthInsurancePlan == null) {
			return new ResponseEntity<Response>(new Response("Failed, Please try again", null), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Response>(new Response("Success", getAllHealthInsurancePlanLinks(healthInsurancePlan,
				new Resource<HealthInsurancePlan>(healthInsurancePlan))), HttpStatus.CREATED);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> updateHealthInsurancePlan(@PathVariable("id") long id,
			@RequestBody @Valid HealthInsurancePlan updateHealthInsurancePlan)
			throws ObjectNotFoundException, InvalidUserException {

		if (healthInsurancePlanService.getByIdAndIsActive(id, true) != null) {
			updateHealthInsurancePlan.setId(id);

			HealthInsurancePlan updatedHealthInsurancePlan = healthInsurancePlanService
					.saveHealthInsurancePlan(updateHealthInsurancePlan);

			if (updatedHealthInsurancePlan != null) {
				return new ResponseEntity<Response>(
						new Response("Success", getAllHealthInsurancePlanLinks(updatedHealthInsurancePlan,
								new Resource<HealthInsurancePlan>(updatedHealthInsurancePlan))),
						HttpStatus.OK);

			} else {
				return new ResponseEntity<Response>(new Response("Failed, Please try again", null),
						HttpStatus.BAD_REQUEST);
			}

		} else {
			throw new ObjectNotFoundException("Health Insurance Plan for given Id does not exists.");
		}

	}

	// for updating single field no need to give all data like in putMapping
	@PatchMapping("/{id}")
	public ResponseEntity<Response> activateHealthInsurancePlan(@PathVariable("id") long id,
			@RequestBody Map<String, Object> fields) throws ObjectNotFoundException, InvalidUserException {

		HealthInsurancePlan healthInsurancePlan = healthInsurancePlanService.getById(id);

		if (healthInsurancePlan != null) {

			// Map key is field name, v is value
			fields.forEach((k, v) -> {
				// use reflection to get field k on manager and set it to value v
				Field field = ReflectionUtils.findRequiredField(HealthInsurancePlan.class, k);
				ReflectionUtils.setField(field, healthInsurancePlan, v);
			});

			HealthInsurancePlan updatedHealthInsurancePlan = healthInsurancePlanService
					.saveHealthInsurancePlan(healthInsurancePlan);

			if (updatedHealthInsurancePlan != null) {
				return new ResponseEntity<Response>(
						new Response("Success", getAllHealthInsurancePlanLinks(updatedHealthInsurancePlan,
								new Resource<HealthInsurancePlan>(updatedHealthInsurancePlan))),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(new Response("Failed, Please try again", null),
						HttpStatus.BAD_REQUEST);
			}

		} else {
			throw new ObjectNotFoundException("Health Insurance Plan for given Id does not exists.");
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteHealthInsurancePlan(@PathVariable("id") long id)
			throws ObjectNotFoundException, InvalidUserException {

		if (healthInsurancePlanService.getByIdAndIsActive(id, true) == null) {
			throw new ObjectNotFoundException("Health Insurance Plan with provided id does not exists.");
		}

		int updateStatus = healthInsurancePlanService.updateStatus(false, id);
		if (updateStatus == 0) {
			return new ResponseEntity<Response>(new Response("Failed, Please try again", null), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Response>(
				new Response("Success", getAllHealthInsurancePlanLinks(null, new Resource<HealthInsurancePlan>(null))),
				HttpStatus.OK);

	}

	public Resource<HealthInsurancePlan> getAllHealthInsurancePlanLinks(HealthInsurancePlan healthInsurancePlan,
			Resource<HealthInsurancePlan> resource) throws ObjectNotFoundException, InvalidUserException {

		Map<String, Object> map = new HashMap<>();
		map.put("isActive", true);

		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass())
						.getHealthInsurancePlanById(healthInsurancePlan.getId()))
				.withRel("health-insurance-plan-by-id").withType("GET"));

		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllHealthInsurancePlan())
				.withRel("all-health-insurance-plan").withType("GET"));

		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass())
						.getFeaturesOfHealthInsurancePlan(healthInsurancePlan.getId()))
				.withRel("features-of-health-insurance-plan-by-id").withType("GET"));

		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass())
						.getHospitalsOfHealthInsurancePlan(healthInsurancePlan.getId()))
				.withRel("hospitals-of-health-insurance-plan-by-id").withType("GET"));

		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).saveHealthInsurancePlan(healthInsurancePlan))
				.withRel("save-health-insurance-plan").withType("POST"));

		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass())
						.updateHealthInsurancePlan(healthInsurancePlan.getId(), healthInsurancePlan))
				.withRel("update-health-insurance-plan").withType("PUT"));

		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass())
						.activateHealthInsurancePlan(healthInsurancePlan.getId(), map))
				.withRel("activate-health-insurance-plan").withType("PATCH"));

		resource.add(ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(this.getClass())
						.deleteHealthInsurancePlan(healthInsurancePlan.getId()))
				.withRel("delete-health-insurance-plan").withType("DELETE"));

		return resource;
	}

}

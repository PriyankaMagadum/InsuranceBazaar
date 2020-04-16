package com.synechron.insurancebazaar.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.insurancebazaar.config.Response;
import com.synechron.insurancebazaar.exception.ObjectNotFoundException;
import com.synechron.insurancebazaar.modal.Feature;
import com.synechron.insurancebazaar.modal.Hospital;
import com.synechron.insurancebazaar.service.FeatureService;

@RestController
public class FeatureController {

	@Autowired
	private Feature feature;

	@Autowired
	private FeatureService featureService;

	@PostMapping("/feature")
	public ResponseEntity<Response> saveFeature(@RequestBody @Valid Feature newFeature) throws ObjectNotFoundException {

		feature = featureService.saveFeature(newFeature);

		if (feature == null) {
			return new ResponseEntity<Response>(new Response("Failed, Please try again.", null),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Response>(
				new Response("Succcess", getAllFeatureLinks(feature, new Resource<Feature>(feature))),
				HttpStatus.CREATED);

	}

	@GetMapping("/feature")
	public ResponseEntity<Response> getFeatureByIsActive() {

		List<Feature> list = new ArrayList<>();
		list = featureService.getFeatureByIsActive(true);

		if (list.size() == 0) {
			return new ResponseEntity<Response>(new Response("Features does not exists.", null), HttpStatus.CREATED);
		}

		List<Resource<Feature>> featureList = list.stream().map(t -> {
			try {
				return getAllFeatureLinks(t, new Resource<Feature>(t));
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}).filter(t -> t != null).collect(Collectors.toList());

		return new ResponseEntity<Response>(new Response("Succcess", featureList), HttpStatus.OK);
	}

	public Resource<Feature> getAllFeatureLinks(Feature feature, Resource<Feature> resource)
			throws ObjectNotFoundException {

		resource.add(
				ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getFeatureByIsActive())
						.withRel("feature-by-isActive").withType("GET"));

		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).saveFeature(feature))
				.withRel("save-feature").withType("POST"));

		return resource;
	}

}

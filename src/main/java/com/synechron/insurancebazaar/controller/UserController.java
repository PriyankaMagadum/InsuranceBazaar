package com.synechron.insurancebazaar.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.synechron.insurancebazaar.config.Response;
import com.synechron.insurancebazaar.exception.ObjectNotFoundException;
import com.synechron.insurancebazaar.modal.Feature;
import com.synechron.insurancebazaar.modal.User;
import com.synechron.insurancebazaar.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<Response> getUserById(@PathVariable("id") long id) throws ObjectNotFoundException {

		User user = userService.getUserById(id);
		if (user == null) {
			throw new ObjectNotFoundException("User with given id does not exists.");
		}

		return new ResponseEntity<Response>(new Response("Success", getAllFeatureLinks(user, new Resource<User>(user))),
				HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Response> saveUser(@RequestBody @Valid User newUser) throws ObjectNotFoundException {

		User user = userService.SaveUser(newUser);
		if (user == null) {
			return new ResponseEntity<Response>(new Response("Failed, Please try again.", null),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Response>(new Response("Success", getAllFeatureLinks(user, new Resource<User>(user))),
				HttpStatus.CREATED);
	}

	public Resource<User> getAllFeatureLinks(User user, Resource<User> resource) throws ObjectNotFoundException {

		resource.add(
				ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getUserById(user.getId()))
						.withRel("user-by-id").withType("GET"));

		resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).saveUser(user))
				.withRel("save-user").withType("POST"));

		return resource;
	}

}

package com.synechron.insurancebazaar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.insurancebazaar.config.Response;
import com.synechron.insurancebazaar.exception.ObjectNotFoundException;
import com.synechron.insurancebazaar.modal.Role;
import com.synechron.insurancebazaar.service.RoleService;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/role")
	public ResponseEntity<Response> getAllRole() throws ObjectNotFoundException {
		List<Role> roles = roleService.getAllRoles();
		if (roles.size() == 0) {
			throw new ObjectNotFoundException("Roles does not exists");
		}
		return new ResponseEntity<Response>(new Response("Success", roles), HttpStatus.OK);
	}
}

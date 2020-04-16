package com.synechron.insurancebazaar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.insurancebazaar.modal.Role;
import com.synechron.insurancebazaar.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public Role getRoleById(long id) {
		Optional<Role> role = roleRepository.findById(id);
		return role.isPresent() ? role.get() : null;
	}

	public Role getRoleByRoleName(String role) {
		Optional<Role> userRole = roleRepository.findByRole(role);
		return userRole.isPresent() ? userRole.get() : null;
	}

}

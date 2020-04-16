package com.synechron.insurancebazaar.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.insurancebazaar.exception.InvalidUserException;
import com.synechron.insurancebazaar.exception.ObjectNotFoundException;
import com.synechron.insurancebazaar.modal.HealthInsurancePlan;
import com.synechron.insurancebazaar.modal.User;
import com.synechron.insurancebazaar.repository.HealthInsurancePlanRepository;

@Service
public class HealthInsurancePlanService {

	@Autowired
	private HealthInsurancePlanRepository healthInsurancePlanRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	public HealthInsurancePlan saveHealthInsurancePlan(HealthInsurancePlan plan)
			throws ObjectNotFoundException, InvalidUserException {

		User user = userService.getUserById(plan.getInsuranceProvider().getId());
		if (user == null) {
			throw new ObjectNotFoundException("Provided insurance provider does not exists.");
		} else if (!user.getRoles().contains(roleService.getRoleByRoleName("INSURANCEPROVIDER"))) {
			throw new InvalidUserException("Invalid Insurance Provider.");
		} else {
			return healthInsurancePlanRepository.saveAndFlush(plan);
		}

	}

	public HealthInsurancePlan getByIdAndIsActive(long id, boolean isActive) {
		Optional<HealthInsurancePlan> healthIsurancePlan = healthInsurancePlanRepository.findByIdAndIsActive(id,
				isActive);
		return healthIsurancePlan.isPresent() ? healthIsurancePlan.get() : null;
	}

	public HealthInsurancePlan getById(long id) {
		Optional<HealthInsurancePlan> healthIsurancePlan = healthInsurancePlanRepository.findById(id);
		return healthIsurancePlan.isPresent() ? healthIsurancePlan.get() : null;
	}

	public List<HealthInsurancePlan> getByIsActive(boolean isActive) {
		return healthInsurancePlanRepository.findByIsActive(isActive);
	}

	@Transactional
	public int updateStatus(boolean isActive, long id) {

		return healthInsurancePlanRepository.updateStatus(isActive, id);
	}
}

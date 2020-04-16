package com.synechron.insurancebazaar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.insurancebazaar.repository.PolicyHolderRepository;

@Service
public class PolicyHolderService {

	@Autowired
	private PolicyHolderRepository policyHolderRepository;

}

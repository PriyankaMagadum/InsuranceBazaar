package com.synechron.insurancebazaar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.insurancebazaar.modal.Feature;
import com.synechron.insurancebazaar.repository.FeatureRepository;

@Service
public class FeatureService {

	@Autowired
	private FeatureRepository featureRepository;

	public List<Feature> getFeatureByIsActive(boolean isActive) {
		return featureRepository.findByIsActive(isActive);
	}

	public Feature saveFeature(Feature feature) {
		return featureRepository.saveAndFlush(feature);
	}

	public Feature getById(long id) {
		return featureRepository.getOne(id);
	}

}

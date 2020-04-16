package com.synechron.insurancebazaar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synechron.insurancebazaar.modal.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

	public List<Feature> findByIsActive(boolean isActive);

}

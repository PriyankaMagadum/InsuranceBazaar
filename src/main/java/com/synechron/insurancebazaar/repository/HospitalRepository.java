package com.synechron.insurancebazaar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synechron.insurancebazaar.modal.City;
import com.synechron.insurancebazaar.modal.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

	public List<Hospital> findByCity(City city);

}

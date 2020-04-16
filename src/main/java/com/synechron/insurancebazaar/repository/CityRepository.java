package com.synechron.insurancebazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synechron.insurancebazaar.modal.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}

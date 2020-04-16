package com.synechron.insurancebazaar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synechron.insurancebazaar.modal.HealthInsurancePlan;

@Repository
public interface HealthInsurancePlanRepository extends JpaRepository<HealthInsurancePlan, Long> {

	public List<HealthInsurancePlan> findByIsActive(boolean isActive);

	@Modifying
	@Query("UPDATE HealthInsurancePlan SET isActive=:isActive WHERE id=:id")
	public int updateStatus(@Param("isActive") boolean isActive, @Param("id") long id);

	public Optional<HealthInsurancePlan> findByIdAndIsActive(long id, boolean isActive);

}

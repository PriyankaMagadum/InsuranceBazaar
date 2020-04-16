package com.synechron.insurancebazaar.modal;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
@Component
@Entity
@Table(name = "tbl_health_insurance_plan")
@JsonIgnoreProperties("")
public class HealthInsurancePlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String planName;

	private long coverMin;

	private long coverMax;

	@Transient
	private double premiumMonthly;

	@Transient
	private double premiumAnnually;

	@ManyToMany
	@JoinTable(name = "tbl_health_insurance_plan_features", uniqueConstraints = @UniqueConstraint(columnNames = {
			"features_id", "HealthInsurancePlan_id" }))
	private Set<Feature> features;

	@ManyToMany
	@JoinTable(name = "tbl_health_insurance_plan_hospitals", uniqueConstraints = @UniqueConstraint(columnNames = {
			"hospitals_id", "HealthInsurancePlan_id" }))
	private Set<Hospital> hospitals;

	@ManyToOne
	private User insuranceProvider;

	@ColumnDefault(value = "1")
	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean isActive = true;

}

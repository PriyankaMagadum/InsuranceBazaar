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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

	@NotBlank(message = "Plan name can not be empty.")
	@Size(min = 3, message = "Plan name should be at least 3 character long.")
	@NotNull(message = "Plan name can not be null.")
	private String planName;

	@Min(value = 1, message = "cover minimum should be greater than 0")
	private long coverMin;

	@Min(value = 1, message = "cover maximum should be greater than 0")
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

	@NotNull(message = "Insurance Provider can not be null.")
	@ManyToOne
	private User insuranceProvider;

	@ColumnDefault(value = "1")
	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean isActive = true;

}

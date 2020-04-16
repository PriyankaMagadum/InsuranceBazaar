package com.synechron.insurancebazaar.modal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "tbl_hospital")
public class Hospital implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "Hospital name can not be empty.")
	@Size(min = 2, message = "Hospital name should be at least 2 character long.")
	@NotNull(message = "Hospital name can not be null.")
	private String hospitalName;

	@NotNull(message = "City can not be null.")
	@OneToOne
	private City city;

	@Min(value = 100000, message = "Pincode should be 6 digit long")
	@Max(value = 999999, message = "Pincode should be 6 digit long")
	private int pincode;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ColumnDefault(value = "1")
	private boolean isActive = true;

}

package com.synechron.insurancebazaar.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
@Component
@Entity
@Table(name = "tbl_feature")
public class Feature {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "Feature can not be empty.")
	@Size(min = 3, message = "Feature should be at least 3 character long.")
	@NotNull(message = "Feature can not be null.")
	@Column(unique = true)
	private String feature;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(columnDefinition = "tinyint(1) default 1")
	private boolean isActive = true;

}

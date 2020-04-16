package com.synechron.insurancebazaar.modal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
@Entity
@Table(name="tbl_policy_holder")
public class PolicyHolder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String member;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ColumnDefault(value = "1")
	private boolean isActive;

}

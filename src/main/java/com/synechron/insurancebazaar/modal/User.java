package com.synechron.insurancebazaar.modal;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "Name can not be empty.")
	@Size(min = 2, message = "Name should be at least 2 character long.")
	@NotNull(message = "Name can not be null.")
	private String name;

	@NotBlank(message = "Email address can not be empty.")
	@NotNull(message = "Email address can not be null.")
	@Email(message = "Invalid email address.")
	private String emailAddress;

	@Size(max = 10, min = 10, message = "Mobile number should be 10 digit long.")
	private String mobileNo;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany
	@JoinTable(name = "tbl_user_role", indexes = @Index(columnList = "roles_id"))
	private Set<Role> roles;

}

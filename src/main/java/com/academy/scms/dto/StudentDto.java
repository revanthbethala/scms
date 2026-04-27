package com.academy.scms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {
	private Integer id;
	@NotBlank
	private String name;
	@NotBlank(message = "Email is required")
	@Email(message = "Please provide a valid email address")
	private String email;
	@NotBlank
	@Size(min = 8, message = "Password should be atleast 8 chars")
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "StudentDto [id=" + id + ", name=" + name + ", email=" + email + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}

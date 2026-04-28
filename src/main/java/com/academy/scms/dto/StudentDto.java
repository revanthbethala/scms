package com.academy.scms.dto;

import com.academy.scms.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {
	private Integer id;
	@NotBlank(groups = OnRegister.class)
	private String name;
	@NotBlank(groups = { OnRegister.class, OnLogin.class })
	@Email
	private String email;
	@NotBlank(groups = { OnRegister.class, OnLogin.class })
	private String password;
	private UserRole role = UserRole.STUDENT;

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

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

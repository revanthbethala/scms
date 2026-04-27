package com.academy.scms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class UserEntity {
	@Id
	private Long id;
	@Column(unique = true)
	private String email;
	private String password;
	private String role;
	@OneToOne
	private StudentEntity studentProfile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public StudentEntity getStudentProfile() {
		return studentProfile;
	}

	public void setStudentProfile(StudentEntity studentProfile) {
		this.studentProfile = studentProfile;
	}
	
}

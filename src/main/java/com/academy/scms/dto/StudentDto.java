package com.academy.scms.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentDto {
	private Integer id;
	@NotBlank
	private String name;
	@NotBlank
	private String email;
	@NotBlank
	@Size(min = 8, message = "Password should be atleast 8 chars")
	private String password;
	private List<CourseSummaryDto> courses;

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

	public List<CourseSummaryDto> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseSummaryDto> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "StudentDto [id=" + id + ", name=" + name + ", email=" + email + ", courses=" + courses + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}

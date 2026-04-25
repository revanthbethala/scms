package com.academy.scms.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CourseDto {
	private Integer id;
	@NotBlank
	@Min(value = 3, message = "Atleast 3 chras needed")
	private String title;
	@NotBlank
	@Min(value = 10, message = "atleast 10 chars needed")
	private String description;

	private List<StudentDto> students;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<StudentDto> getStudents() {
		return students;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStudents(List<StudentDto> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "CourseDto [id=" + id + ", title=" + title + ", students=" + students + "]";
	}
}
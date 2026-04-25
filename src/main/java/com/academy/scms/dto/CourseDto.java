package com.academy.scms.dto;

import java.util.List;

public class CourseDto {
	private Integer id;
	private String title;
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

	public void setStudents(List<StudentDto> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "CourseDto [id=" + id + ", title=" + title + ", students=" + students + "]";
	}
}
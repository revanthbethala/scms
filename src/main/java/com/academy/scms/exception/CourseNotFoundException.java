package com.academy.scms.exception;

public class CourseNotFoundException extends RuntimeException {

	public CourseNotFoundException() {
		super("Course was not found.");
	}

	public CourseNotFoundException(int id) {
		super("Course not found with id:" + id);
	}
}
package com.academy.scms.exception;

public class CourseNotFoundException extends RuntimeException {

	public CourseNotFoundException() {
		super("Course was not found.");
	}

	public CourseNotFoundException(String message) {
		super(message);
	}
}
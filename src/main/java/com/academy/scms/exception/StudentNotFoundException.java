package com.academy.scms.exception;

public class StudentNotFoundException extends RuntimeException {

	public StudentNotFoundException(int id) {
		super("Student not found with id" + id);
	}
}

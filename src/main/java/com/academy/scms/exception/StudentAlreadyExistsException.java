package com.academy.scms.exception;

public class StudentAlreadyExistsException extends RuntimeException {

	public StudentAlreadyExistsException() {
		super("Student already exists.");
	}

}

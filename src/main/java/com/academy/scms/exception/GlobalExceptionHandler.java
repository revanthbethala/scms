package com.academy.scms.exception;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.academy.scms.utils.ErrorResponseUtil;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(CourseNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleCourseNotFound(CourseNotFoundException ex) {

		log.warn("Course not found: {}", ex.getMessage());

		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.NOT_FOUND, ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> handleParse(HttpMessageNotReadableException ex) {

		String message = "Invalid JSON format";

		if (ex.getCause() != null) {
			message = ex.getCause().getMessage();
		}

		log.warn("Invalid JSON received: {}", message);

		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, message);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Map<String, Object>> handle404(NoHandlerFoundException ex) {

		log.warn("404 Not Found: {} {}", ex.getHttpMethod(), ex.getRequestURL());

		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.NOT_FOUND, "API endpoint not found");

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handle500(Exception ex) {

		log.error("Unhandled exception occurred", ex); // full stack trace

		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.INTERNAL_SERVER_ERROR,
				"Something went wrong");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
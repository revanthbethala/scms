package com.academy.scms.exception;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

	@ExceptionHandler(StudentAlreadyExistsException.class)
	public ResponseEntity<Map<String, Object>> handleExists(StudentAlreadyExistsException ex) {
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.CONFLICT, ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleStudentNotFound(StudentNotFoundException ex) {
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.NOT_FOUND, ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, Object>> handleExistsinDB(DataIntegrityViolationException ex) {
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.CONFLICT, ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, Object>> handleMethArgs(MethodArgumentTypeMismatchException ex) {
		log.warn("Invalid args recieve: {}", ex.getMessage());
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "invalid parameter values");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Map<String, Object>> handleAccess(AccessDeniedException ex) {
		log.warn("Invalid args recieve: {}", ex.getMessage());
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.FORBIDDEN, ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<Map<String, Object>> handleCourseNotFound(NoResourceFoundException ex) {
		log.warn("Route not found: {}", ex.getMessage());
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
	public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
		log.warn("404 Not Found: {} {}", ex.getHttpMethod(), ex.getRequestURL());
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.NOT_FOUND, "API endpoint not found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, Object>> handleNotSupported(HttpRequestMethodNotSupportedException ex) {
		log.warn("Method Not Allowed: {}", ex.getMethod());
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.METHOD_NOT_ALLOWED,
				"The " + ex.getMethod() + " method is not supported for this endpoint.");
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<Map<String, Object>> handleDBException(DataAccessException ex) {
		log.error("DB exception", ex);
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error at DB");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handle500(Exception ex) {
		log.error("Unhandled exception", ex);
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.INTERNAL_SERVER_ERROR,
				"Something went wrong");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
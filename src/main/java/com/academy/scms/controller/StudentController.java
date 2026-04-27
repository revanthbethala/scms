package com.academy.scms.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academy.scms.dto.StudentDto;
import com.academy.scms.enums.UserRole;
import com.academy.scms.exception.StudentAlreadyExistsException;
import com.academy.scms.exception.StudentNotFoundException;
import com.academy.scms.security.RequireRole;
import com.academy.scms.service.StudentService;
import com.academy.scms.utils.ErrorResponseUtil;
import com.academy.scms.utils.SuccessResponseUtil;
import com.academy.scms.utils.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<Object> getAllStudents(@RequestAttribute("subject") Integer studentId) {
		return SuccessResponseUtil.successResponse(studentService.getAllStudents(studentId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getStudentById(@PathVariable Integer id,
			@RequestAttribute("subject") Integer studentId) {
		return SuccessResponseUtil.successResponse(studentService.getStudentById(id, studentId));
	}

	@GetMapping("/{id}/courses")
	public ResponseEntity<Object> getStudentEnrolledCourses(@PathVariable Integer id) {
		return SuccessResponseUtil.successResponse(studentService.getStudentEnrolledCourses(id));
	}

	@GetMapping("/search")
	public ResponseEntity<Object> searchByName(@RequestParam String name,
			@RequestAttribute("subject") Integer loggedInId) {
		return SuccessResponseUtil.successResponse(studentService.searchByName(name, loggedInId));
	}

	@PostMapping
	@RequireRole(UserRole.ADMIN)
	public ResponseEntity<Object> createStudent(@Valid @RequestBody StudentDto dto, BindingResult result) {
		Map<String, String> validationErrors = ValidationUtil.getValidationErrors(result);
		if (!validationErrors.isEmpty()) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
			error.put("error", validationErrors);
			return ResponseEntity.badRequest().body(error);
		}

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(Map.of("message", "Success", "data", studentService.createStudent(dto)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentDto dto,
			BindingResult result) {
		Map<String, String> validationErrors = ValidationUtil.getValidationErrors(result);
		if (!validationErrors.isEmpty()) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
			error.put("error", validationErrors);
			return ResponseEntity.badRequest().body(error);
		}
		return SuccessResponseUtil.successResponse(studentService.updateStudent(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable Integer id) {
		studentService.deleteStudent(id);
		return ResponseEntity.ok(Map.of("message", "Success"));
	}

	@PostMapping("/{id}/enroll/{cId}")
	public ResponseEntity<Object> enrollCourse(@PathVariable Integer id, @PathVariable Integer cId) {
		return SuccessResponseUtil.successResponse(studentService.enrollCourse(id, cId));
	}

	@DeleteMapping("/{id}/enroll/{cId}")
	public ResponseEntity<Object> unenrollCourse(@PathVariable Integer id, @PathVariable Integer cId) {
		return SuccessResponseUtil.successResponse(studentService.unenrollCourse(id, cId));
	}



}
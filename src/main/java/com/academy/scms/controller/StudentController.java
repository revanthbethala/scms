package com.academy.scms.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academy.scms.dto.StudentDto;
import com.academy.scms.exception.StudentAlreadyExistsException;
import com.academy.scms.exception.StudentNotFoundException;
import com.academy.scms.service.StudentService;
import com.academy.scms.utils.ErrorResponseUtil;
import com.academy.scms.utils.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	// GET all students
	@GetMapping
	public ResponseEntity<Object> getAllStudents() {

		List<StudentDto> students = studentService.getAllStudents();

		return ResponseEntity.ok(Map.of("message", "Students fetched successfully", "data", students));
	}

	// GET student by id
	@GetMapping("/{id}")
	public ResponseEntity<Object> getStudentById(@PathVariable Integer id) {

		StudentDto student = studentService.getStudentById(id);

		return ResponseEntity.ok(Map.of("message", "Student fetched successfully", "data", student));
	}

	// SEARCH
	@GetMapping("/search")
	public ResponseEntity<Object> searchByName(@RequestParam String name) {

		List<StudentDto> students = studentService.searchByName(name);

		return ResponseEntity.ok(Map.of("message", "Search completed", "data", students));
	}

	// CREATE
	@PostMapping
	public ResponseEntity<Object> createStudent(@Valid @RequestBody StudentDto dto, BindingResult result) {

		Map<String, String> validationErrors = ValidationUtil.getValidationErrors(result);

		if (!validationErrors.isEmpty()) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
			error.put("validationErrors", validationErrors);

			return ResponseEntity.badRequest().body(error);
		}

		StudentDto created = studentService.createStudent(dto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(Map.of("message", "Student created successfully", "data", created));
	}

	// UPDATE
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentDto dto,
			BindingResult result) {

		Map<String, String> validationErrors = ValidationUtil.getValidationErrors(result);

		if (!validationErrors.isEmpty()) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
			error.put("validationErrors", validationErrors);

			return ResponseEntity.badRequest().body(error);
		}

		StudentDto updated = studentService.updateStudent(id, dto);

		return ResponseEntity.ok(Map.of("message", "Student updated successfully", "data", updated));
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable Integer id) {

		studentService.deleteStudent(id);

		return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
	}

	// ENROLL
	@PostMapping("/{id}/enroll/{cId}")
	public ResponseEntity<Object> enrollCourse(@PathVariable Integer id, @PathVariable Integer cId) {

		StudentDto updated = studentService.enrollCourse(id, cId);

		return ResponseEntity.ok(Map.of("message", "Course enrolled successfully", "data", updated));
	}

	// UNENROLL
	@DeleteMapping("/{id}/enroll/{cId}")
	public ResponseEntity<Object> unenrollCourse(@PathVariable Integer id, @PathVariable Integer cId) {

		StudentDto updated = studentService.unenrollCourse(id, cId);

		return ResponseEntity.ok(Map.of("message", "Course unenrolled successfully", "data", updated));
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

}
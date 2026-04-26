package com.academy.scms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academy.scms.dto.CourseDto;
import com.academy.scms.dto.StudentDto;
import com.academy.scms.exception.InvalidDataFormatException;
import com.academy.scms.service.CourseService;
import com.academy.scms.utils.ErrorResponseUtil;
import com.academy.scms.utils.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	public ResponseEntity<Object> getAllCourses() {

		List<CourseDto> courses = courseService.getAllCourses();

		return ResponseEntity.ok(Map.of("message", "Success", "data", courses));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getCourseById(@PathVariable Integer id) {

		CourseDto course = courseService.getCourseById(id);

		return ResponseEntity.ok(Map.of("message", "Success", "data", course));
	}

	@GetMapping("/search")
	public ResponseEntity<Object> searchByTitle(@RequestParam String title) {
		if (title == null || title.isBlank()) {
			throw new InvalidDataFormatException("Invalid title name");
		}

		List<CourseDto> courses = courseService.searchByTitle(title);

		return ResponseEntity.ok(Map.of("message", "Success", "data", courses));
	}

	@GetMapping("/{id}/students")
	public ResponseEntity<Object> getStudents(@PathVariable Integer id) {

		List<StudentDto> students = courseService.getStudentsByCourse(id);

		return ResponseEntity.ok(Map.of("message", "Success", "data", students));
	}

	@PostMapping
	public ResponseEntity<Object> createCourse(@Valid @RequestBody CourseDto dto, BindingResult result) {

		Map<String, String> errors = ValidationUtil.getValidationErrors(result);

		if (!errors.isEmpty()) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
			error.put("error", errors);

			return ResponseEntity.badRequest().body(error);
		}

		CourseDto created = courseService.createCourse(dto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(Map.of("message", "Course created successfully", "data", created));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateCourse(@PathVariable Integer id, @Valid @RequestBody CourseDto dto,
			BindingResult result) {

		Map<String, String> errors = ValidationUtil.getValidationErrors(result);

		if (!errors.isEmpty()) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
			error.put("validationErrors", errors);

			return ResponseEntity.badRequest().body(error);
		}

		CourseDto updated = courseService.updateCourse(id, dto);

		return ResponseEntity.ok(Map.of("message", "Success", "data", updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCourse(@PathVariable Integer id) {

		courseService.deleteCourse(id);

		return ResponseEntity.ok(Map.of("message", "Success"));
	}

}
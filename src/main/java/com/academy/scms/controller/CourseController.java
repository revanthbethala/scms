package com.academy.scms.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.academy.scms.dto.CourseDto;
import com.academy.scms.enums.UserRole;
import com.academy.scms.exception.InvalidDataFormatException;
import com.academy.scms.security.RequireRole;
import com.academy.scms.service.CourseService;
import com.academy.scms.utils.ErrorResponseUtil;
import com.academy.scms.utils.SuccessResponseUtil;
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
		return SuccessResponseUtil.successResponse(courseService.getAllCourses());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getCourseById(@PathVariable Integer id) {
		return SuccessResponseUtil.successResponse(courseService.getCourseById(id));
	}

	@GetMapping("/search")
	public ResponseEntity<Object> searchByTitle(@RequestParam String title) {
		if (title == null || title.isBlank()) {
			throw new InvalidDataFormatException("Invalid title name");
		}
		return SuccessResponseUtil.successResponse(courseService.searchByTitle(title));
	}

	@GetMapping("/{id}/students")
	public ResponseEntity<Object> getStudents(@PathVariable Integer id,
			@RequestAttribute("subject") Integer loggedInId) {
		return SuccessResponseUtil.successResponse(courseService.getStudentsByCourse(id, loggedInId));
	}

	@PostMapping
	@RequireRole(UserRole.ADMIN)
	public ResponseEntity<Object> createCourse(@Valid @RequestBody CourseDto dto, BindingResult result) {
		Map<String, String> errors = ValidationUtil.getValidationErrors(result);
		if (!errors.isEmpty()) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Invalid data");
			error.put("error", errors);
			return ResponseEntity.badRequest().body(error);
		}
		CourseDto created = courseService.createCourse(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(Map.of("message", "Course created successfully", "data", created));
	}

	@PutMapping("/{id}")
	@RequireRole(UserRole.ADMIN)
	public ResponseEntity<Object> updateCourse(@PathVariable Integer id, @RequestAttribute("subject") Integer studentId,
			@Valid @RequestBody CourseDto dto, BindingResult result) {

		Map<String, String> errors = ValidationUtil.getValidationErrors(result);

		if (!errors.isEmpty()) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Invalid data");
			error.put("validationErrors", errors);
			return ResponseEntity.badRequest().body(error);
		}

		return SuccessResponseUtil.successResponse(courseService.updateCourse(id, studentId, dto));
	}

	@DeleteMapping("/{id}")
	@RequireRole(UserRole.ADMIN)
	public ResponseEntity<Object> deleteCourse(@PathVariable Integer id,
			@RequestAttribute("subject") Integer studentId) {
		courseService.deleteCourse(id, studentId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

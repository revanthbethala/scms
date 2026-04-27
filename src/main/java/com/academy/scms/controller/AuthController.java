package com.academy.scms.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.academy.scms.dto.StudentDto;
import com.academy.scms.exception.InvalidCredentialsException;
import com.academy.scms.service.AuthService;
import com.academy.scms.utils.ErrorResponseUtil;
import com.academy.scms.utils.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody StudentDto registrationDto, BindingResult result) {
		Map<String, String> validationErrors = ValidationUtil.getValidationErrors(result);

		if (!validationErrors.isEmpty()) {
			Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Invalid data");
			error.put("error", validationErrors);
			return ResponseEntity.badRequest().body(error);
		}
		StudentDto student = authService.registrationService(registrationDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(Map.of("message", "User registered successfully", "data", student));
	}

	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@Valid @RequestBody StudentDto loginDto, BindingResult result) {
		System.out.println("err:" + result.hasErrors());
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(ValidationUtil.getValidationErrors(result));
		}

		String token = authService.loginService(loginDto);

		return ResponseEntity.ok(Map.of("message", "Login successful", "token", token));
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
		Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.UNAUTHORIZED, ex.getMessage());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

}
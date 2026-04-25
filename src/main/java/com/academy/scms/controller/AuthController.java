package com.academy.scms.controller;

import com.academy.scms.dto.LoginDto;
import com.academy.scms.dto.RegistrationDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationDto registrationDto) {
		// Logic to save user to database goes here
		return ResponseEntity.ok("User registered successfully!");
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDto loginDto) {
		// Logic to authenticate user goes here
		return ResponseEntity.ok("Login successful!");
	}
}

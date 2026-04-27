package com.academy.scms.service;

import org.springframework.stereotype.Service;

import com.academy.scms.dto.StudentDto;
import com.academy.scms.entity.StudentEntity;
import com.academy.scms.enums.UserRole;
import com.academy.scms.exception.InvalidCredentialsException;
import com.academy.scms.exception.StudentAlreadyExistsException;
import com.academy.scms.mapper.StudentMapper;
import com.academy.scms.repository.StudentRepository;
import com.academy.scms.security.JwtUtil;

@Service
public class AuthService {

	private StudentRepository studentRepository;
	private JwtUtil jwtUtil;
	private StudentMapper studentMapper;

	public AuthService(StudentRepository studentRepository, JwtUtil jwtUtil, StudentMapper studentMapper) {
		this.studentRepository = studentRepository;
		this.jwtUtil = jwtUtil;
		this.studentMapper = studentMapper;
	}

	public String loginService(StudentDto loginDto) {

		StudentEntity student = studentRepository.findByEmail(loginDto.getEmail());

		if (student == null || !student.getPassword().equals(loginDto.getPassword())) {
			throw new InvalidCredentialsException("Invalid email or password");
		}

		StudentDto dto = studentMapper.toDto(student);

		return jwtUtil.generateToken(dto);
	}

	public StudentDto registrationService(StudentDto dto) {

		if (studentRepository.findByEmail(dto.getEmail()) != null) {
			throw new StudentAlreadyExistsException();
		}

//		if (!dto.getPassword().equals(dto.getConfirmPassword())) {
//			throw new InvalidCredentialsException("Password and confirm password should be same");
//		}

		StudentEntity entity = new StudentEntity();
		if (dto.getRole() == null) {
			entity.setRole(UserRole.STUDENT);
		} else {
			entity.setRole(dto.getRole());
		}
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		StudentEntity saved = studentRepository.save(entity);
		return studentMapper.toDto(saved);
	}
}
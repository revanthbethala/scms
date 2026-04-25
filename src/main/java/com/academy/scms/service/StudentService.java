package com.academy.scms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academy.scms.entity.StudentEntity;
import com.academy.scms.exception.StudentAlreadyExistsException;
import com.academy.scms.exception.StudentNotFoundException;
import com.academy.scms.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	// CREATE
	public StudentEntity addStudent(StudentEntity student) {
		studentRepository.findByEmail(student.getEmail()).ifPresent(s -> {
			throw new StudentAlreadyExistsException("Student with email " + student.getEmail() + " already exists.");
		});
		return studentRepository.save(student);
	}

	// READ (All)
	public List<StudentEntity> getAllStudents() {
		return studentRepository.findAll();
	}

	// READ (By ID)
	public StudentEntity getStudentById(Integer id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found."));
	}

	// UPDATE
	public StudentEntity updateStudent(Integer id, StudentEntity studentDetails) {
		StudentEntity existingStudent = getStudentById(id);
		existingStudent.setName(studentDetails.getName());
		existingStudent.setEmail(studentDetails.getEmail());
		return studentRepository.save(existingStudent);
	}

	// DELETE
	public void deleteStudent(Integer id) {
		if (!studentRepository.existsById(id)) {
			throw new StudentNotFoundException("Cannot delete. Student ID " + id + " not found.");
		}
		studentRepository.deleteById(id);
	}
}

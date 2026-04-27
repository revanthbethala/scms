package com.academy.scms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.academy.scms.dto.CourseDto;
import com.academy.scms.dto.StudentDto;
import com.academy.scms.entity.CourseEntity;
import com.academy.scms.entity.StudentEntity;
import com.academy.scms.exception.CourseNotFoundException;
import com.academy.scms.exception.StudentNotFoundException;
import com.academy.scms.mapper.CourseMapper;
import com.academy.scms.mapper.StudentMapper;
import com.academy.scms.repository.CourseRepository;
import com.academy.scms.repository.StudentRepository;

@Service
public class StudentService {

	private final CourseMapper courseMapper;
	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final StudentMapper studentMapper;

	public StudentService(StudentRepository studentRepository, CourseRepository courseRepository,
			StudentMapper studentMapper, CourseMapper courseMapper) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.studentMapper = studentMapper;
		this.courseMapper = courseMapper;
	}

	public List<StudentDto> getAllStudents(Integer loggedInId) {
		List<StudentEntity> entities = studentRepository.findAll();
		if (entities.isEmpty()) {
			throw new StudentNotFoundException("No students exists");
		}
		List<StudentDto> dtos = studentMapper.toDtoList(entities, loggedInId);
		return dtos;
	}

	public StudentDto getStudentById(Integer id, Integer loggedInId) {
		Optional<StudentEntity> optional = studentRepository.findById(id);

		if (optional.isPresent()) {
			StudentDto dto = studentMapper.toDto(optional.get(), loggedInId);
			return dto;
		} else {
			throw new StudentNotFoundException(id);
		}
	}

	public List<CourseDto> getStudentEnrolledCourses(Integer id) {
		Optional<StudentEntity> optional = studentRepository.findById(id);

		if (optional.isPresent()) {
			StudentEntity entity = optional.get();
			List<CourseEntity> courseEntities = entity.getCourses();
			if (courseEntities != null && !courseEntities.isEmpty()) {
				return courseEntities.stream().map(course -> courseMapper.toDto(course)).collect(Collectors.toList());
			} else {
				return Collections.emptyList();
			}
		} else {
			throw new StudentNotFoundException(id);
		}
	}

	public List<StudentDto> searchByName(String name, Integer loggedInId) {
		List<StudentEntity> list = studentRepository.findByNameContainingIgnoreCase(name);

		if (list.isEmpty()) {
			throw new StudentNotFoundException("No students found with name: " + name);
		}
		List<StudentDto> dtoList = studentMapper.toDtoList(list, loggedInId);
		return dtoList;
	}

	public StudentDto createStudent(StudentDto dto) {
		System.out.println("dto:" + dto);
		StudentEntity entity = studentMapper.toEntity(dto);

		if (entity.getCourses() == null) {
			entity.setCourses(new ArrayList<>());
		}
		System.out.println("entity:" + entity);
		StudentEntity saved = studentRepository.save(entity);
		return studentMapper.toDto(saved);
	}

	public StudentDto updateStudent(Integer id, StudentDto dto) {

		Optional<StudentEntity> optional = studentRepository.findById(id);

		if (optional.isPresent()) {
			StudentEntity existing = optional.get();
			existing.setName(dto.getName());
			existing.setEmail(dto.getEmail());
			existing.setPassword(dto.getPassword());

			StudentEntity updated = studentRepository.save(existing);
			return studentMapper.toDto(updated);

		} else {
			throw new StudentNotFoundException(id);
		}
	}

	public void deleteStudent(Integer id) {

		Optional<StudentEntity> optional = studentRepository.findById(id);

		if (optional.isPresent()) {
			studentRepository.delete(optional.get());
		} else {
			throw new StudentNotFoundException(id);
		}
	}

	public StudentDto enrollCourse(Integer studentId, Integer courseId) {

		Optional<StudentEntity> studentOpt = studentRepository.findById(studentId);
		Optional<CourseEntity> courseOpt = courseRepository.findById(courseId);

		if (!studentOpt.isPresent()) {
			throw new StudentNotFoundException(studentId);
		}

		if (!courseOpt.isPresent()) {
			throw new CourseNotFoundException(courseId);
		}

		StudentEntity student = studentOpt.get();
		CourseEntity course = courseOpt.get();

		if (student.getCourses() == null) {
			student.setCourses(new ArrayList<>());
		}

		if (!student.getCourses().contains(course)) {
			student.getCourses().add(course);
		}
		if (!course.getStudents().contains(student)) {
			course.getStudents().add(student);
		}

		StudentEntity saved = studentRepository.save(student);
		return studentMapper.toDto(saved);
	}

	public StudentDto unenrollCourse(Integer studentId, Integer courseId) {

		Optional<StudentEntity> studentOpt = studentRepository.findById(studentId);
		Optional<CourseEntity> courseOpt = courseRepository.findById(courseId);

		if (!studentOpt.isPresent()) {
			throw new StudentNotFoundException(studentId);
		}

		if (!courseOpt.isPresent()) {
			throw new CourseNotFoundException(courseId);
		}

		StudentEntity student = studentOpt.get();
		CourseEntity course = courseOpt.get();

		if (student.getCourses() != null) {
			student.getCourses().remove(course);
		}
		if (!course.getStudents().contains(student)) {
			course.getStudents().remove(student);
		}

		StudentEntity saved = studentRepository.save(student);
		return studentMapper.toDto(saved);
	}
}
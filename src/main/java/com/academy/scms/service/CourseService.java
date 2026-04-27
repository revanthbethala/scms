package com.academy.scms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.academy.scms.dto.CourseDto;
import com.academy.scms.dto.StudentDto;
import com.academy.scms.entity.CourseEntity;
import com.academy.scms.entity.StudentEntity;
import com.academy.scms.exception.AccessDeniedException;
import com.academy.scms.exception.CourseNotFoundException;
import com.academy.scms.mapper.CourseMapper;
import com.academy.scms.mapper.StudentMapper;
import com.academy.scms.repository.CourseRepository;

@Service
public class CourseService {

	private final CourseRepository courseRepository;
	private final CourseMapper courseMapper;
	private final StudentMapper studentMapper;

	public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, StudentMapper studentMapper) {
		this.courseRepository = courseRepository;
		this.courseMapper = courseMapper;
		this.studentMapper = studentMapper;
	}

	public List<CourseDto> getAllCourses() {
	    List<CourseEntity> courses = courseRepository.findAll();
	    if (courses.isEmpty()) {
	        throw new CourseNotFoundException("No courses available");
	    }
	    return courseMapper.toDtoList(courses);
	}
	public CourseDto getCourseById(Integer id) {

		Optional<CourseEntity> optional = courseRepository.findById(id);

		if (optional.isPresent()) {
			return courseMapper.toDto(optional.get());
		} else {
			throw new CourseNotFoundException(id);
		}
	}

	public List<CourseDto> searchByTitle(String title) {
	    List<CourseEntity> courses = courseRepository.findByTitleContainingIgnoreCase(title);
	    if (courses.isEmpty()) {
	        throw new CourseNotFoundException("No courses found with title: " + title);
	    }
	    return courseMapper.toDtoList(courses);
	}

	public List<StudentDto> getStudentsByCourse(Integer id,Integer loggedInId) {

		Optional<CourseEntity> optional = courseRepository.findById(id);

		if (!optional.isPresent()) {
			throw new CourseNotFoundException(id);
		}
		List<StudentEntity> students = optional.get().getStudents();
		return studentMapper.toDtoList(students);
	}

	public CourseDto createCourse(CourseDto dto) {

		CourseEntity entity = courseMapper.toEntity(dto);

		CourseEntity saved = courseRepository.save(entity);

		return courseMapper.toDto(saved);
	}

	public CourseDto updateCourse(Integer id, Integer studentId, CourseDto dto) {

		Optional<CourseEntity> optional = courseRepository.findById(id);

		if (optional.isPresent()) {
			CourseEntity existing = optional.get();

			if (existing.getCreatedBy() != studentId) {
				throw new AccessDeniedException("You can't modfiy this course");
			}
			existing.setTitle(dto.getTitle());

			CourseEntity updated = courseRepository.save(existing);

			return courseMapper.toDto(updated);
		} else {
			throw new CourseNotFoundException(id);
		}
	}

	public void deleteCourse(Integer id, Integer studentId) {

		Optional<CourseEntity> optional = courseRepository.findById(id);

		if (optional.isPresent()) {

			CourseEntity course = optional.get();
			if (course.getCreatedBy() != studentId) {
				throw new AccessDeniedException("You can't modfiy this course");
			}
			for (StudentEntity student : course.getStudents()) {
				student.getCourses().remove(course);
			}
			courseRepository.delete(course);
		} else {
			throw new CourseNotFoundException(id);
		}
	}
}
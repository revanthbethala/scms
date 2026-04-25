package com.academy.scms.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.academy.scms.dto.CourseDto;
import com.academy.scms.dto.StudentDto;
import com.academy.scms.entity.CourseEntity;
import com.academy.scms.entity.StudentEntity;
import com.academy.scms.exception.CourseNotFoundException;
import com.academy.scms.mapper.CourseMapper;
import com.academy.scms.mapper.StudentMapper;
import com.academy.scms.repository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;

    public CourseService(CourseRepository courseRepository,
                         CourseMapper courseMapper,
                         StudentMapper studentMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
    }

    // GET ALL
    public List<CourseDto> getAllCourses() {
        return courseMapper.toDtoList(courseRepository.findAll());
    }

    // GET BY ID
    public CourseDto getCourseById(Integer id) {

        Optional<CourseEntity> optional = courseRepository.findById(id);

        if (optional.isPresent()) {
            return courseMapper.toDto(optional.get());
        } else {
            throw new CourseNotFoundException(id);
        }
    }

    // SEARCH
    public List<CourseDto> searchByTitle(String title) {
        return courseMapper.toDtoList(
                courseRepository.findByTitleContainingIgnoreCase(title)
        );
    }

    // GET STUDENTS OF COURSE
    public List<StudentDto> getStudentsByCourse(Integer id) {

        Optional<CourseEntity> optional = courseRepository.findById(id);

        if (!optional.isPresent()) {
            throw new CourseNotFoundException(id);
        }

        List<StudentEntity> students = optional.get().getStudents();

        return studentMapper.toDtoList(students);
    }

    // CREATE
    public CourseDto createCourse(CourseDto dto) {

        CourseEntity entity = courseMapper.toEntity(dto);

        CourseEntity saved = courseRepository.save(entity);

        return courseMapper.toDto(saved);
    }

    // UPDATE
    public CourseDto updateCourse(Integer id, CourseDto dto) {

        Optional<CourseEntity> optional = courseRepository.findById(id);

        if (optional.isPresent()) {
            CourseEntity existing = optional.get();

            existing.setTitle(dto.getTitle());

            CourseEntity updated = courseRepository.save(existing);

            return courseMapper.toDto(updated);
        } else {
            throw new CourseNotFoundException(id);
        }
    }

    // DELETE
    public void deleteCourse(Integer id) {

        Optional<CourseEntity> optional = courseRepository.findById(id);

        if (optional.isPresent()) {
            courseRepository.delete(optional.get());
        } else {
            throw new CourseNotFoundException(id);
        }
    }
}
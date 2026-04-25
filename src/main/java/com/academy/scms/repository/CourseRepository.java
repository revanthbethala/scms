package com.academy.scms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academy.scms.entity.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {

}

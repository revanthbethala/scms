package com.academy.scms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.scms.entity.CourseEntity;
@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
    List<CourseEntity> findByTitleContainingIgnoreCase(String title);

}

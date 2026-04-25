package com.academy.scms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academy.scms.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

	public StudentEntity findByEmail(String email);
}

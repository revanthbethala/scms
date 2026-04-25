package com.academy.scms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.scms.entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

	public StudentEntity findByEmail(String email);

	public List<StudentEntity> findByNameContainingIgnoreCase(String name);
}

package com.academy.scms.mapper;

import java.util.List;

import com.academy.scms.dto.StudentDto;
import com.academy.scms.entity.StudentEntity;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    // Single object mapping
    StudentDto toDto(StudentEntity entity);
    
    // List mapping (it handles the loop for you!)
    List<StudentDto> toDtoList(List<StudentEntity> entities);
}

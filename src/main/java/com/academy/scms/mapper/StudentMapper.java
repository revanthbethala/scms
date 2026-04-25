package com.academy.scms.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.academy.scms.dto.StudentDto;
import com.academy.scms.entity.StudentEntity;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	@Mapping(target = "courses", source = "courses")

	StudentDto toDto(StudentEntity entity);

	StudentEntity toEntity(StudentDto dto);

	List<StudentDto> toDtoList(List<StudentEntity> entities);
}

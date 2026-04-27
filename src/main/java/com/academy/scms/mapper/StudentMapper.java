package com.academy.scms.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.academy.scms.dto.StudentDto;
import com.academy.scms.entity.StudentEntity;

@Component
public class StudentMapper {

	public StudentDto toDto(StudentEntity entity, Integer loggedInId) {
		if (entity == null)
			return null;

		StudentDto dto = new StudentDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setEmail(entity.getEmail());
		if (loggedInId != null && loggedInId.equals(entity.getId())) {
			dto.setPassword(entity.getPassword());
		}

		return dto;
	}

	public StudentDto toDto(StudentEntity entity) {
		if (entity == null)
			return null;
		StudentDto dto = new StudentDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setEmail(entity.getEmail());
		return dto;
	}

	public List<StudentDto> toDtoList(List<StudentEntity> entities, Integer loggedInId) {
		if (entities == null)
			return null;
		return entities.stream().map(entity -> toDto(entity, loggedInId)).collect(Collectors.toList());
	}

	public List<StudentDto> toDtoList(List<StudentEntity> entities) {
		if (entities == null)
			return null;
		return entities.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
	}

	public StudentEntity toEntity(StudentDto dto) {
		if (dto == null)
			return null;
		StudentEntity entity = new StudentEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;
	}
}

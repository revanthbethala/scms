package com.academy.scms.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.academy.scms.dto.CourseDto;
import com.academy.scms.entity.CourseEntity;

@Component
public class CourseMapper {

	public CourseDto toDto(CourseEntity entity) {
		if (entity == null)
			return null;

		CourseDto dto = new CourseDto();
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		dto.setModifedBy(entity.getModifedBy());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setModifiedAt(entity.getModifiedAt());
		return dto;
	}

	public List<CourseDto> toDtoList(List<CourseEntity> entities) {
		if (entities == null)
			return null;
		return entities.stream().map(this::toDto).collect(Collectors.toList());
	}

	public CourseEntity toEntity(CourseDto dto) {
		if (dto == null)
			return null;
		CourseEntity entity = new CourseEntity();
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		return entity;
	}
}

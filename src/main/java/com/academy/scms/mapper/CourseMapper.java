package com.academy.scms.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.academy.scms.dto.CourseDto;
import com.academy.scms.entity.CourseEntity;

@Mapper(componentModel = "spring")
public interface CourseMapper {
	@Mapping(target = "students", ignore = true) // IMPORTANT

	CourseDto toDto(CourseEntity ce);
	CourseEntity toEntity(CourseDto dto);


	List<CourseDto> toDtoList(List<CourseEntity> se);

}

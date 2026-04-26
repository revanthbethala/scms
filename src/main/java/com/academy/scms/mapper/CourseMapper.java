package com.academy.scms.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.academy.scms.dto.CourseDto;
import com.academy.scms.entity.CourseEntity;

@Mapper(componentModel = "spring")
public interface CourseMapper {
	CourseDto toDto(CourseEntity ce);

	@Mapping(target = "students", ignore = true)
	CourseEntity toEntity(CourseDto dto);

	List<CourseDto> toDtoList(List<CourseEntity> se);

}

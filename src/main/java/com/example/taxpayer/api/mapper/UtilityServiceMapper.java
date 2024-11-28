package com.example.taxpayer.api.mapper;

import com.example.taxpayer.api.dto.UtilityServiceCreationDto;
import com.example.taxpayer.api.dto.UtilityServiceDto;
import com.example.taxpayer.api.entity.UtilityServiceEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UtilityServiceMapper {

    @Mapping(target = "isFixed", source = "isFixed", defaultValue = "false")
    UtilityServiceEntity toEntity(UtilityServiceCreationDto dto);

    UtilityServiceDto toResponseDto(UtilityServiceEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UtilityServiceDto dto, @MappingTarget UtilityServiceEntity entity);
}


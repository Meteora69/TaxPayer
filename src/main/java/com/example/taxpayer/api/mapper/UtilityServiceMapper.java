package com.example.taxpayer.api.mapper;

import com.example.taxpayer.api.dto.UtilityServiceCreationDto;
import com.example.taxpayer.api.dto.UtilityServiceDto;
import com.example.taxpayer.api.entity.UtilityServiceEntity;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UtilityServiceMapper {

    // Мапінг DTO -> Entity
    UtilityServiceEntity toEntity(UtilityServiceCreationDto dto);

    // Мапінг Entity -> DTO
    UtilityServiceDto toResponseDto(UtilityServiceEntity entity);

    // Часткове оновлення сутності з DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UtilityServiceDto dto, @MappingTarget UtilityServiceEntity entity);
}


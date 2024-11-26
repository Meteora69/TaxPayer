package com.example.taxpayer.api.mapper;


import com.example.taxpayer.api.dto.AddressCreationDto;
import com.example.taxpayer.api.dto.AddressDto;
import com.example.taxpayer.api.entity.AddressEntity;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AddressMapper {

    // Автоматичне мапінг DTO -> Entity
    AddressEntity toEntity(AddressCreationDto dto);

    // Автоматичне мапінг Entity -> DTO
    AddressDto toDto(AddressEntity entity);

    // Часткове оновлення сутності з DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AddressDto dto, @MappingTarget AddressEntity entity);
}
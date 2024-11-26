package com.example.taxpayer.api.mapper;

import com.example.taxpayer.api.dto.BillCreationDto;
import com.example.taxpayer.api.dto.BillDto;
import com.example.taxpayer.api.entity.BillEntity;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface BillMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "service.id", target = "serviceId")
    BillDto toDto(BillEntity entity);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "addressId", target = "address.id")
    @Mapping(source = "serviceId", target = "service.id")
    BillEntity toEntity(BillCreationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BillDto dto, @MappingTarget BillEntity entity);
}

package com.example.taxpayer.api.mapper;

import com.example.taxpayer.api.dto.PaymentCreationDto;
import com.example.taxpayer.api.dto.PaymentDto;
import com.example.taxpayer.api.entity.PaymentEntity;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface PaymentMapper {

    // Перетворення сутності у DTO для відповіді
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "bill.id", target = "billId")
    PaymentDto toDto(PaymentEntity paymentEntity);

    // Перетворення DTO для створення у сутність
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "billId", target = "bill.id")
    PaymentEntity toEntity(PaymentCreationDto paymentCreationDto);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "billId", target = "bill.id")
    void updateEntityFromDto(PaymentDto paymentDto, @MappingTarget PaymentEntity paymentEntity);
    // Оновлення сутності
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PaymentEntity partialUpdate(PaymentDto paymentDto, @MappingTarget PaymentEntity paymentEntity);
}


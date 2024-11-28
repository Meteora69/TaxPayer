package com.example.taxpayer.api.mapper;

import com.example.taxpayer.api.dto.UserCreationDto;
import com.example.taxpayer.api.dto.UserDto;
import com.example.taxpayer.api.entity.UserEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper {

    // Перетворення сутності у DTO для відповіді
    UserDto toDto(UserEntity userEntity);

    @Mapping(target = "id", ignore = true) // ID генерується базою даних
    @Mapping(target = "password", ignore = true) // Пароль обробляється окремо
    UserEntity toEntity(UserCreationDto userCreationDto);

    // Оновлення існуючої сутності користувача
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserDto userDto, @MappingTarget UserEntity userEntity);
}

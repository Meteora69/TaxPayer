package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.UserCreationDto;
import com.example.taxpayer.api.dto.UserDto;
import com.example.taxpayer.api.entity.UserEntity;
import com.example.taxpayer.api.mapper.UserMapper;
import com.example.taxpayer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taxpayer.api.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto createUser(UserCreationDto userCreationDto) {
        logger.info("Creating user with data: {}", userCreationDto); // Логування створення користувача
        UserEntity userEntity = userMapper.toEntity(userCreationDto);
        UserEntity savedEntity = userRepository.save(userEntity);
        logger.info("User created with ID: {}", savedEntity.getId()); // Логування успішного створення
        return userMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) throws UserNotFoundException {
        logger.info("Fetching user with ID: {}", id); // Логування запиту на отримання користувача
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", id); // Логування попередження, якщо користувач не знайдений
                    return new UserNotFoundException("User not found with ID: " + id);
                });
        logger.info("User fetched: {}", userEntity); // Логування отриманого користувача
        return userMapper.toDto(userEntity);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        logger.info("Fetching all users."); // Логування запиту на отримання всіх користувачів
        List<UserDto> userDtos = userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Fetched {} users.", userDtos.size()); // Логування кількості отриманих користувачів
        return userDtos;
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException {
        logger.info("Updating user with ID: {}", id); // Логування запиту на оновлення користувача
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", id); // Логування попередження, якщо користувач не знайдений
                    return new UserNotFoundException("User not found with ID: " + id);
                });

        userMapper.partialUpdate(userDto, existingUser);
        UserEntity updatedEntity = userRepository.save(existingUser);
        logger.info("User with ID {} updated successfully.", updatedEntity.getId()); // Логування успішного оновлення
        return userMapper.toDto(updatedEntity);
    }

    @Transactional
    public void deleteUser(Long id) throws UserNotFoundException {
        logger.info("Attempting to delete user with ID: {}", id); // Логування запиту на видалення користувача
        if (!userRepository.existsById(id)) {
            logger.warn("Cannot delete user - ID not found: {}", id); // Логування попередження, якщо користувач не знайдений
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
        logger.info("User with ID {} deleted successfully.", id); // Логування успішного видалення
    }
}

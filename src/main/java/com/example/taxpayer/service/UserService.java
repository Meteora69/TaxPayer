package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.UserCreationDto;
import com.example.taxpayer.api.dto.UserDto;
import com.example.taxpayer.api.entity.UserEntity;
import com.example.taxpayer.api.mapper.UserMapper;
import com.example.taxpayer.repository.UserRepository;
import com.example.taxpayer.api.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
   // private final PasswordEncoder passwordEncoder; // Ін'єкція PasswordEncoder

    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true), // Очищення кешу для всіх користувачів
            @CacheEvict(value = "users", key = "#result.id") // Очищення кешу для конкретного користувача
    })
    @Transactional
    public UserDto createUser(UserCreationDto userCreationDto) {
        logger.info("Creating user with data: {}", userCreationDto);

        // Шифрування пароля перед мапінгом
        UserEntity userEntity = userMapper.toEntity(userCreationDto);
       // userEntity.setPassword(passwordEncoder.encode(userCreationDto.password()));

        UserEntity savedEntity = userRepository.save(userEntity);
        logger.info("User created with ID: {}", savedEntity.getId());
        return userMapper.toDto(savedEntity);
    }

    @Cacheable(value = "users", key = "#id")
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) throws UserNotFoundException {
        logger.info("Fetching user with ID: {}", id);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found with ID: " + id);
                });
        logger.info("User fetched: {}", userEntity);
        return userMapper.toDto(userEntity);
    }

    @Cacheable(value = "users")
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        logger.info("Fetching all users.");
        List<UserDto> userDtos = userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Fetched {} users.", userDtos.size());
        return userDtos;
    }

    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true), // Очищення кешу для всіх користувачів
            @CacheEvict(value = "users", key = "#id") // Очищення кешу для конкретного користувача
    })
    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException {
        logger.info("Updating user with ID: {}", id);
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found with ID: " + id);
                });

        userMapper.partialUpdate(userDto, existingUser);
        UserEntity updatedEntity = userRepository.save(existingUser);
        logger.info("User with ID {} updated successfully.", updatedEntity.getId());
        return userMapper.toDto(updatedEntity);
    }

    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true), // Очищення кешу для всіх користувачів
            @CacheEvict(value = "users", key = "#id") // Очищення кешу для конкретного користувача
    })
    @Transactional
    public void deleteUser(Long id) throws UserNotFoundException {
        logger.info("Attempting to delete user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            logger.warn("Cannot delete user - ID not found: {}", id);
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
        logger.info("User with ID {} deleted successfully.", id);
    }
}

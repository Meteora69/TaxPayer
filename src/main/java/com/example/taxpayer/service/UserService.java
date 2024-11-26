package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.UserCreationDto;
import com.example.taxpayer.api.dto.UserDto;
import com.example.taxpayer.api.entity.UserEntity;
import com.example.taxpayer.api.mapper.UserMapper;
import com.example.taxpayer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import com.example.taxpayer.api.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto createUser(UserCreationDto userCreationDto) {
        UserEntity userEntity = userMapper.toEntity(userCreationDto);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return userMapper.toDto(userEntity);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        userMapper.partialUpdate(userDto, existingUser);
        UserEntity updatedEntity = userRepository.save(existingUser);
        return userMapper.toDto(updatedEntity);
    }

    @Transactional
    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}

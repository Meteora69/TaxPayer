package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.UtilityServiceCreationDto;
import com.example.taxpayer.api.dto.UtilityServiceDto;
import com.example.taxpayer.api.entity.UtilityServiceEntity;
import com.example.taxpayer.api.mapper.UtilityServiceMapper;
import com.example.taxpayer.repository.UtilityServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UtilityServiceService {

    private final UtilityServiceRepository repository;
    private final UtilityServiceMapper mapper;

    @CacheEvict(value = "utilityServices", allEntries = true)
    @Transactional
    public UtilityServiceDto create(UtilityServiceCreationDto creationDto) {
        UtilityServiceEntity entity = mapper.toEntity(creationDto);
        return mapper.toResponseDto(repository.save(entity));
    }

    @CacheEvict(value = "utilityServices", key = "#id")
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @CacheEvict(value = "utilityServices", key = "#id")
    @Transactional
    public UtilityServiceDto update(Long id, UtilityServiceDto updateDto) {
        return repository.findById(id)
                .map(existingEntity -> {
                    mapper.updateEntityFromDto(updateDto, existingEntity);
                    return mapper.toResponseDto(repository.save(existingEntity));
                })
                .orElseThrow(() -> new IllegalArgumentException("Utility Service with ID " + id + " not found"));
    }

    @Cacheable(value = "utilityServices", key = "#page + '-' + #size")
    public Page<UtilityServiceDto> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size))
                .map(mapper::toResponseDto);
    }

    @Cacheable(value = "utilityServices", key = "#id")
    public UtilityServiceDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new IllegalArgumentException("Utility Service with ID " + id + " not found"));
    }
}


package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.UtilityServiceCreationDto;
import com.example.taxpayer.api.dto.UtilityServiceDto;
import com.example.taxpayer.api.entity.UtilityServiceEntity;
import com.example.taxpayer.api.mapper.UtilityServiceMapper;
import com.example.taxpayer.repository.UtilityServiceRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UtilityServiceService.class);
    private final UtilityServiceRepository repository;
    private final UtilityServiceMapper mapper;

    @CacheEvict(value = "utilityServices", allEntries = true)
    @Transactional
    public UtilityServiceDto create(UtilityServiceCreationDto creationDto) {
        logger.info("Creating utility service with data: {}", creationDto); // Логування створення
        UtilityServiceEntity entity = mapper.toEntity(creationDto);
        UtilityServiceDto responseDto = mapper.toResponseDto(repository.save(entity));
        logger.info("Utility service created with ID: {}", responseDto.id()); // Логування успішного створення
        return responseDto;
    }

    @CacheEvict(value = "utilityServices", key = "#id")
    @Transactional
    public void delete(Long id) {
        logger.info("Deleting utility service with ID: {}", id); // Логування запиту на видалення
        if (!repository.existsById(id)) {
            logger.warn("Utility service with ID: {} not found", id); // Логування попередження, якщо не знайдена
            throw new IllegalArgumentException("Utility Service with ID " + id + " not found");
        }
        repository.deleteById(id);
        logger.info("Utility service with ID: {} deleted successfully", id); // Логування успішного видалення
    }

    @CacheEvict(value = "utilityServices", key = "#id")
    @Transactional
    public UtilityServiceDto update(Long id, UtilityServiceDto updateDto) {
        logger.info("Updating utility service with ID: {}", id); // Логування запиту на оновлення
        return repository.findById(id)
                .map(existingEntity -> {
                    mapper.updateEntityFromDto(updateDto, existingEntity);
                    UtilityServiceDto updatedDto = mapper.toResponseDto(repository.save(existingEntity));
                    logger.info("Utility service with ID: {} updated successfully", id); // Логування успішного оновлення
                    return updatedDto;
                })
                .orElseThrow(() -> {
                    logger.warn("Utility service with ID: {} not found", id); // Логування попередження про відсутність
                    return new IllegalArgumentException("Utility Service with ID " + id + " not found");
                });
    }

    @Cacheable(value = "utilityServices", key = "#page + '-' + #size")
    public Page<UtilityServiceDto> getAll(int page, int size) {
        logger.info("Fetching all utility services - page: {}, size: {}", page, size); // Логування запиту на отримання всіх
        Page<UtilityServiceDto> utilityServices = repository.findAll(PageRequest.of(page, size))
                .map(mapper::toResponseDto);
        logger.info("Fetched {} utility services.", utilityServices.getTotalElements()); // Логування кількості отриманих
        return utilityServices;
    }

    @Cacheable(value = "utilityServices", key = "#id")
    public UtilityServiceDto getById(Long id) {
        logger.info("Fetching utility service with ID: {}", id); // Логування запиту на отримання
        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> {
                    logger.warn("Utility service with ID: {} not found", id); // Логування попередження про відсутність
                    return new IllegalArgumentException("Utility Service with ID " + id + " not found");
                });
    }
}

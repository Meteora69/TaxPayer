package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.AddressCreationDto;
import com.example.taxpayer.api.dto.AddressDto;
import com.example.taxpayer.api.entity.AddressEntity;
import com.example.taxpayer.api.mapper.AddressMapper;
import com.example.taxpayer.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Caching(evict = {
            @CacheEvict(value = "addresses", allEntries = true), // Очищення кешу для всіх записів
            @CacheEvict(value = "addresses", key = "#result.id") // Очищення кешу для конкретної адреси
    })
    @Transactional
    public AddressDto create(AddressCreationDto addressCreationDto) {
        logger.info("Creating address with data: {}", addressCreationDto);
        AddressEntity addressEntity = addressMapper.toEntity(addressCreationDto);
        AddressEntity savedEntity = addressRepository.save(addressEntity);
        logger.info("Address created with ID: {}", savedEntity.getId());
        return addressMapper.toDto(savedEntity);
    }

    @Caching(evict = {
            @CacheEvict(value = "addresses", allEntries = true), // Очищення кешу для всіх записів
            @CacheEvict(value = "addresses", key = "#id") // Очищення кешу для конкретної адреси
    })
    @Transactional
    public AddressDto update(Long id, AddressDto addressDto) {
        logger.info("Updating address with ID: {}", id);
        AddressEntity existingEntity = addressRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Address not found with ID: {}", id);
                    return new IllegalArgumentException("Address with ID " + id + " not found");
                });
        addressMapper.updateEntityFromDto(addressDto, existingEntity);
        AddressEntity updatedEntity = addressRepository.save(existingEntity);
        logger.info("Address with ID {} updated successfully.", updatedEntity.getId());
        return addressMapper.toDto(updatedEntity);
    }

    @Cacheable(value = "addresses", key = "#id")
    public AddressDto getById(Long id) {
        logger.info("Fetching address with ID: {}", id);
        AddressEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Address not found with ID: {}", id);
                    return new IllegalArgumentException("Address with ID " + id + " not found");
                });
        logger.info("Address fetched: {}", entity);
        return addressMapper.toDto(entity);
    }

    @Cacheable(value = "addresses", key = "#page + '-' + #size")
    public Page<AddressDto> getAll(int page, int size) {
        logger.info("Fetching all addresses - page: {}, size: {}", page, size);
        Page<AddressDto> addressDtos = addressRepository.findAll(PageRequest.of(page, size))
                .map(addressMapper::toDto);
        logger.info("Fetched {} addresses.", addressDtos.getTotalElements());
        return addressDtos;
    }

    @Caching(evict = {
            @CacheEvict(value = "addresses", allEntries = true), // Очищення кешу для всіх записів
            @CacheEvict(value = "addresses", key = "#id") // Очищення кешу для конкретної адреси
    })
    @Transactional
    public void delete(Long id) {
        logger.info("Attempting to delete address with ID: {}", id);
        if (!addressRepository.existsById(id)) {
            logger.warn("Cannot delete address - ID not found: {}", id);
            throw new IllegalArgumentException("Address with ID " + id + " not found");
        }
        addressRepository.deleteById(id);
        logger.info("Address with ID {} deleted successfully.", id);
    }
}

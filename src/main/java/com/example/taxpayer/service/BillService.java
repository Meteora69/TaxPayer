package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.BillCreationDto;
import com.example.taxpayer.api.dto.BillDto;
import com.example.taxpayer.api.entity.BillEntity;
import com.example.taxpayer.api.mapper.BillMapper;
import com.example.taxpayer.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taxpayer.api.exception.BillNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillService.class);
    private final BillRepository billRepository;
    private final BillMapper billMapper;

    @Caching(evict = {
            @CacheEvict(value = "bills", allEntries = true),
            @CacheEvict(value = "bills", key = "#result.id")
    })
    @Transactional
    public BillDto createBill(BillCreationDto billCreationDto) {
        logger.info("Creating bill with data: {}", billCreationDto);
        BillEntity billEntity = billMapper.toEntity(billCreationDto);
        BillEntity savedEntity = billRepository.save(billEntity);
        logger.info("Bill created with ID: {}", savedEntity.getId());
        return billMapper.toDto(savedEntity);
    }

    @Cacheable(value = "bills", key = "#id")
    @Transactional(readOnly = true)
    public BillDto getBillById(Long id) throws BillNotFoundException {
        logger.info("Fetching bill with ID: {}", id);
        BillEntity billEntity = billRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Bill not found with ID: {}", id);
                    return new BillNotFoundException("Bill not found with ID: " + id);
                });
        logger.info("Bill fetched: {}", billEntity);
        return billMapper.toDto(billEntity);
    }

    // Оновлений метод для фільтрації рахунків
    @Cacheable(value = "bills")
    @Transactional(readOnly = true)
    public List<BillDto> getAllBills(Long userId, Double minAmount, Double maxAmount, String dueDate, int page, int size) {
        logger.info("Fetching bills with filters: userId={}, minAmount={}, maxAmount={}, dueDate={}", userId, minAmount, maxAmount, dueDate);

        Pageable pageable = PageRequest.of(page, size);
        Specification<BillEntity> spec = BillSpecifications.filterBills(userId, minAmount, maxAmount, dueDate);

        Page<BillEntity> billsPage = billRepository.findAll(spec, pageable);
        List<BillDto> billDtos = billsPage.getContent().stream()
                .map(billMapper::toDto)
                .collect(Collectors.toList());

        logger.info("Fetched {} bills after filtering.", billDtos.size());
        return billDtos;
    }


    @Caching(evict = {
            @CacheEvict(value = "bills", allEntries = true),
            @CacheEvict(value = "bills", key = "#id")
    })
    @Transactional
    public BillDto updateBill(Long id, BillDto billDto) throws BillNotFoundException {
        logger.info("Updating bill with ID: {}", id);
        BillEntity existingBill = billRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Bill not found with ID: {}", id);
                    return new BillNotFoundException("Bill not found with ID: " + id);
                });

        billMapper.updateEntityFromDto(billDto, existingBill);
        BillEntity updatedEntity = billRepository.save(existingBill);
        logger.info("Bill with ID {} updated successfully.", updatedEntity.getId());
        return billMapper.toDto(updatedEntity);
    }

    @Caching(evict = {
            @CacheEvict(value = "bills", allEntries = true),
            @CacheEvict(value = "bills", key = "#id")
    })
    @Transactional
    public void deleteBill(Long id) throws BillNotFoundException {
        logger.info("Attempting to delete bill with ID: {}", id);
        if (!billRepository.existsById(id)) {
            logger.warn("Cannot delete bill - ID not found: {}", id);
            throw new BillNotFoundException("Bill not found with ID: " + id);
        }
        billRepository.deleteById(id);
        logger.info("Bill with ID {} deleted successfully.", id);
    }
}
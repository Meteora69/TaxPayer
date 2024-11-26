package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.BillCreationDto;
import com.example.taxpayer.api.dto.BillDto;
import com.example.taxpayer.api.entity.BillEntity;
import com.example.taxpayer.api.mapper.BillMapper;
import com.example.taxpayer.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Transactional
    public BillDto createBill(BillCreationDto billCreationDto) {
        logger.info("Creating bill with data: {}", billCreationDto); // Логування створення рахунку
        BillEntity billEntity = billMapper.toEntity(billCreationDto);
        BillEntity savedEntity = billRepository.save(billEntity);
        logger.info("Bill created with ID: {}", savedEntity.getId()); // Логування успішного створення
        return billMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public BillDto getBillById(Long id) throws BillNotFoundException {
        logger.info("Fetching bill with ID: {}", id); // Логування запиту на отримання рахунку
        BillEntity billEntity = billRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Bill not found with ID: {}", id); // Логування попередження, якщо рахунок не знайдений
                    return new BillNotFoundException("Bill not found with ID: " + id);
                });
        logger.info("Bill fetched: {}", billEntity); // Логування отриманого рахунку
        return billMapper.toDto(billEntity);
    }

    @Transactional(readOnly = true)
    public List<BillDto> getAllBills() {
        logger.info("Fetching all bills."); // Логування запиту на отримання всіх рахунків
        List<BillDto> billDtos = billRepository.findAll().stream()
                .map(billMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Fetched {} bills.", billDtos.size()); // Логування кількості отриманих рахунків
        return billDtos;
    }

    @Transactional
    public BillDto updateBill(Long id, BillDto billDto) throws BillNotFoundException {
        logger.info("Updating bill with ID: {}", id); // Логування запиту на оновлення рахунку
        BillEntity existingBill = billRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Bill not found with ID: {}", id); // Логування попередження, якщо рахунок не знайдений
                    return new BillNotFoundException("Bill not found with ID: " + id);
                });

        billMapper.updateEntityFromDto(billDto, existingBill);
        BillEntity updatedEntity = billRepository.save(existingBill);
        logger.info("Bill with ID {} updated successfully.", updatedEntity.getId()); // Логування успішного оновлення
        return billMapper.toDto(updatedEntity);
    }

    @Transactional
    public void deleteBill(Long id) throws BillNotFoundException {
        logger.info("Attempting to delete bill with ID: {}", id); // Логування запиту на видалення рахунку
        if (!billRepository.existsById(id)) {
            logger.warn("Cannot delete bill - ID not found: {}", id); // Логування попередження, якщо рахунок не знайдений
            throw new BillNotFoundException("Bill not found with ID: " + id);
        }
        billRepository.deleteById(id);
        logger.info("Bill with ID {} deleted successfully.", id); // Логування успішного видалення
    }
}

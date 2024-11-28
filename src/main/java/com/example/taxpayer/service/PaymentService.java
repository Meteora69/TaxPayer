package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.PaymentCreationDto;
import com.example.taxpayer.api.dto.PaymentDto;
import com.example.taxpayer.api.entity.PaymentEntity;
import com.example.taxpayer.api.mapper.PaymentMapper;
import com.example.taxpayer.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taxpayer.api.exception.PaymentNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Caching(evict = {
            @CacheEvict(value = "payments", allEntries = true),
            @CacheEvict(value = "payments", key = "#result.id")
    })
    @Transactional
    public PaymentDto createPayment(PaymentCreationDto paymentCreationDto) {
        logger.info("Creating payment with data: {}", paymentCreationDto);
        PaymentEntity paymentEntity = paymentMapper.toEntity(paymentCreationDto);
        PaymentEntity savedEntity = paymentRepository.save(paymentEntity);
        logger.info("Payment created with ID: {}", savedEntity.getId());
        return paymentMapper.toDto(savedEntity);
    }

    @Cacheable(value = "payments", key = "#id")
    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long id) throws PaymentNotFoundException {
        logger.info("Fetching payment with ID: {}", id);
        PaymentEntity paymentEntity = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Payment not found with ID: {}", id);
                    return new PaymentNotFoundException("Payment not found with ID: " + id);
                });
        logger.info("Payment fetched: {}", paymentEntity);
        return paymentMapper.toDto(paymentEntity);
    }

    // Оновлений метод для фільтрації платежів
    @Cacheable(value = "payments")
    @Transactional(readOnly = true)
    public List<PaymentDto> getAllPayments(Long billId, Double minAmount, Double maxAmount, String dueDate, int page, int size) {
        logger.info("Fetching payments with filters: billId={}, minAmount={}, maxAmount={}, dueDate={}", billId, minAmount, maxAmount, dueDate);

        // Створюємо умовний запит для фільтрації
        List<PaymentEntity> payments = paymentRepository.findAll();  // Замість цього ви можете застосувати реальні фільтри в запиті

        // Фільтруємо за billId, minAmount, maxAmount та dueDate
        if (billId != null) {
            payments = payments.stream().filter(payment -> payment.getId().equals(billId)).collect(Collectors.toList());
        }
        if (minAmount != null) {
            payments = payments.stream().filter(payment -> payment.getPaymentAmount() >= minAmount).collect(Collectors.toList());
        }
        if (maxAmount != null) {
            payments = payments.stream().filter(payment -> payment.getPaymentAmount() <= maxAmount).collect(Collectors.toList());
        }
        if (dueDate != null) {
            payments = payments.stream().filter(payment -> payment.getPaymentDate().equals(dueDate)).collect(Collectors.toList());
        }

        // Пагінація
        int start = Math.min(page * size, payments.size());
        int end = Math.min((page + 1) * size, payments.size());
        List<PaymentDto> paymentDtos = payments.subList(start, end).stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());

        logger.info("Fetched {} payments after filtering.", paymentDtos.size());
        return paymentDtos;
    }

    @Caching(evict = {
            @CacheEvict(value = "payments", allEntries = true),
            @CacheEvict(value = "payments", key = "#id")
    })
    @Transactional
    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) throws PaymentNotFoundException {
        logger.info("Updating payment with ID: {}", id);
        PaymentEntity existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Payment not found with ID: {}", id);
                    return new PaymentNotFoundException("Payment not found with ID: " + id);
                });

        paymentMapper.updateEntityFromDto(paymentDto, existingPayment);
        PaymentEntity updatedEntity = paymentRepository.save(existingPayment);
        logger.info("Payment with ID {} updated successfully.", updatedEntity.getId());
        return paymentMapper.toDto(updatedEntity);
    }

    @Caching(evict = {
            @CacheEvict(value = "payments", allEntries = true),
            @CacheEvict(value = "payments", key = "#id")
    })
    @Transactional
    public void deletePayment(Long id) throws PaymentNotFoundException {
        logger.info("Attempting to delete payment with ID: {}", id);
        if (!paymentRepository.existsById(id)) {
            logger.warn("Cannot delete payment - ID not found: {}", id);
            throw new PaymentNotFoundException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
        logger.info("Payment with ID {} deleted successfully.", id);
    }
}

package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.PaymentCreationDto;
import com.example.taxpayer.api.dto.PaymentDto;
import com.example.taxpayer.api.entity.PaymentEntity;
import com.example.taxpayer.api.mapper.PaymentMapper;
import com.example.taxpayer.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Transactional
    public PaymentDto createPayment(PaymentCreationDto paymentCreationDto) {
        logger.info("Creating payment with data: {}", paymentCreationDto); // Логування створення платежу
        PaymentEntity paymentEntity = paymentMapper.toEntity(paymentCreationDto);
        PaymentEntity savedEntity = paymentRepository.save(paymentEntity);
        logger.info("Payment created with ID: {}", savedEntity.getId()); // Логування успішного створення
        return paymentMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long id) throws PaymentNotFoundException {
        logger.info("Fetching payment with ID: {}", id); // Логування запиту на отримання платежу
        PaymentEntity paymentEntity = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Payment not found with ID: {}", id); // Логування попередження, якщо платіж не знайдений
                    return new PaymentNotFoundException("Payment not found with ID: " + id);
                });
        logger.info("Payment fetched: {}", paymentEntity); // Логування отриманого платежу
        return paymentMapper.toDto(paymentEntity);
    }

    @Transactional(readOnly = true)
    public List<PaymentDto> getAllPayments() {
        logger.info("Fetching all payments."); // Логування запиту на отримання всіх платежів
        List<PaymentDto> paymentDtos = paymentRepository.findAll().stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Fetched {} payments.", paymentDtos.size()); // Логування кількості отриманих платежів
        return paymentDtos;
    }

    @Transactional
    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) throws PaymentNotFoundException {
        logger.info("Updating payment with ID: {}", id); // Логування запиту на оновлення платежу
        PaymentEntity existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Payment not found with ID: {}", id); // Логування попередження, якщо платіж не знайдений
                    return new PaymentNotFoundException("Payment not found with ID: " + id);
                });

        paymentMapper.partialUpdate(paymentDto, existingPayment);
        PaymentEntity updatedEntity = paymentRepository.save(existingPayment);
        logger.info("Payment with ID {} updated successfully.", updatedEntity.getId()); // Логування успішного оновлення
        return paymentMapper.toDto(updatedEntity);
    }

    @Transactional
    public void deletePayment(Long id) throws PaymentNotFoundException {
        logger.info("Attempting to delete payment with ID: {}", id); // Логування запиту на видалення платежу
        if (!paymentRepository.existsById(id)) {
            logger.warn("Cannot delete payment - ID not found: {}", id); // Логування попередження, якщо платіж не знайдений
            throw new PaymentNotFoundException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
        logger.info("Payment with ID {} deleted successfully.", id); // Логування успішного видалення
    }
}

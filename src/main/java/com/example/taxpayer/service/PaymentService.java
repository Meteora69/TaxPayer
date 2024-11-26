package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.PaymentCreationDto;
import com.example.taxpayer.api.dto.PaymentDto;
import com.example.taxpayer.api.entity.PaymentEntity;
import com.example.taxpayer.api.mapper.PaymentMapper;
import com.example.taxpayer.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import com.example.taxpayer.api.exception.PaymentNotFoundException;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentDto createPayment(PaymentCreationDto paymentCreationDto) {
        PaymentEntity paymentEntity = paymentMapper.toEntity(paymentCreationDto);
        PaymentEntity savedEntity = paymentRepository.save(paymentEntity);
        return paymentMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long id) throws PaymentNotFoundException {
        PaymentEntity paymentEntity = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));
        return paymentMapper.toDto(paymentEntity);
    }

    @Transactional(readOnly = true)
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) throws PaymentNotFoundException {
        PaymentEntity existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));

        paymentMapper.partialUpdate(paymentDto, existingPayment);
        PaymentEntity updatedEntity = paymentRepository.save(existingPayment);
        return paymentMapper.toDto(updatedEntity);
    }

    @Transactional
    public void deletePayment(Long id) throws PaymentNotFoundException {
        if (!paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
    }
}


package com.example.taxpayer.service;


import com.example.taxpayer.api.dto.BillCreationDto;
import com.example.taxpayer.api.dto.BillDto;
import com.example.taxpayer.api.entity.BillEntity;
import com.example.taxpayer.api.mapper.BillMapper;
import com.example.taxpayer.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.example.taxpayer.api.exception.BillNotFoundException;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final BillMapper billMapper;

    @Transactional
    public BillDto createBill(BillCreationDto billCreationDto) {
        BillEntity billEntity = billMapper.toEntity(billCreationDto);
        BillEntity savedEntity = billRepository.save(billEntity);
        return billMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public BillDto getBillById(Long id) throws BillNotFoundException {
        BillEntity billEntity = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("Bill not found with ID: " + id));
        return billMapper.toDto(billEntity);
    }

    @Transactional(readOnly = true)
    public List<BillDto> getAllBills() {
        return billRepository.findAll().stream()
                .map(billMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BillDto updateBill(Long id, BillDto billDto) throws BillNotFoundException {
        BillEntity existingBill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("Bill not found with ID: " + id));

        billMapper.updateEntityFromDto(billDto, existingBill);
        BillEntity updatedEntity = billRepository.save(existingBill);
        return billMapper.toDto(updatedEntity);
    }

    @Transactional
    public void deleteBill(Long id) throws BillNotFoundException {
        if (!billRepository.existsById(id)) {
            throw new BillNotFoundException("Bill not found with ID: " + id);
        }
        billRepository.deleteById(id);
    }
}

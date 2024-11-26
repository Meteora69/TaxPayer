package com.example.taxpayer.service;

import com.example.taxpayer.api.dto.AddressCreationDto;
import com.example.taxpayer.api.dto.AddressDto;
import com.example.taxpayer.api.entity.AddressEntity;
import com.example.taxpayer.api.mapper.AddressMapper;
import com.example.taxpayer.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public AddressDto create(AddressCreationDto addressCreationDto) {
        AddressEntity addressEntity = addressMapper.toEntity(addressCreationDto);
        AddressEntity savedEntity = addressRepository.save(addressEntity);
        return addressMapper.toDto(savedEntity);
    }

    @Transactional
    public AddressDto update(Long id, AddressDto addressDto) {
        AddressEntity existingEntity = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address with ID " + id + " not found"));
        addressMapper.updateEntityFromDto(addressDto, existingEntity);
        AddressEntity updatedEntity = addressRepository.save(existingEntity);
        return addressMapper.toDto(updatedEntity);
    }

    public AddressDto getById(Long id) {
        AddressEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address with ID " + id + " not found"));
        return addressMapper.toDto(entity);
    }

    public Page<AddressDto> getAll(int page, int size) {
        return addressRepository.findAll(PageRequest.of(page, size))
                .map(addressMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new IllegalArgumentException("Address with ID " + id + " not found");
        }
        addressRepository.deleteById(id);
    }
}

package com.example.taxpayer.api.controller;

import com.example.taxpayer.api.dto.AddressCreationDto;
import com.example.taxpayer.api.dto.AddressDto;
import com.example.taxpayer.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> create(@RequestBody AddressCreationDto addressCreationDto) {
        AddressDto createdAddress = addressService.create(addressCreationDto);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> update(@PathVariable Long id, @RequestBody AddressDto addressDto) {
        AddressDto updatedAddress = addressService.update(id, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getById(@PathVariable Long id) {
        AddressDto address = addressService.getById(id);
        return ResponseEntity.ok(address);
    }

    @GetMapping
    public ResponseEntity<Page<AddressDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AddressDto> addresses = addressService.getAll(page, size);
        return ResponseEntity.ok(addresses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

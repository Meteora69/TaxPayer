package com.example.taxpayer.api.controller;

import com.example.taxpayer.api.dto.UtilityServiceCreationDto;
import com.example.taxpayer.api.dto.UtilityServiceDto;
import com.example.taxpayer.service.UtilityServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utility-services")
@RequiredArgsConstructor
public class UtilityServiceController {

    private final UtilityServiceService service;

    @PostMapping
    public ResponseEntity<UtilityServiceDto> create(@RequestBody UtilityServiceCreationDto creationDto) {
        UtilityServiceDto createdService = service.create(creationDto);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilityServiceDto> update(
            @PathVariable Long id,
            @RequestBody UtilityServiceDto updateDto) {
        UtilityServiceDto updatedService = service.update(id, updateDto);
        return ResponseEntity.ok(updatedService);
    }

    @GetMapping
    public ResponseEntity<Page<UtilityServiceDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilityServiceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}

package com.example.taxpayer.api.controller;

import com.example.taxpayer.api.dto.BillCreationDto;
import com.example.taxpayer.api.dto.BillDto;
import com.example.taxpayer.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping
    public ResponseEntity<BillDto> createBill(@RequestBody BillCreationDto billCreationDto) {
        BillDto createdBill = billService.createBill(billCreationDto);
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDto> getBillById(@PathVariable Long id) {
        BillDto bill = billService.getBillById(id);
        return ResponseEntity.ok(bill);
    }

    @GetMapping
    public ResponseEntity<List<BillDto>> getAllBills() {
        List<BillDto> bills = billService.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDto> updateBill(@PathVariable Long id, @RequestBody BillDto billDto) {
        BillDto updatedBill = billService.updateBill(id, billDto);
        return ResponseEntity.ok(updatedBill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.example.taxpayer.api.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.taxpayer.api.entity.AddressEntity;
import com.example.taxpayer.api.entity.UtilityServiceEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bills")
public class BillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "due_date", nullable = false)
    private String dueDate;

    @Column(name = "is_payed", nullable = false)
    private boolean isPayed;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Зв’язок до користувача
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false) // Зовнішній ключ до адреси
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false) // Зовнішній ключ до послуги
    private UtilityServiceEntity service;
}

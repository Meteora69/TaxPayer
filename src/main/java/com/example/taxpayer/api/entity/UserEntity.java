package com.example.taxpayer.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.example.taxpayer.api.entity.BillEntity;
import com.example.taxpayer.api.entity.PaymentEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance", nullable = false)
    private Double balance;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BillEntity> bills = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentEntity> payments = new ArrayList<>();
}

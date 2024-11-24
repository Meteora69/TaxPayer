package com.example.taxpayer.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_date", nullable = false)
    private String paymentDate;

    @Column(name = "payment_amount", nullable = false)
    private Double paymentAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Посилання на користувача
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "bill_id", nullable = false, unique = true)
    private BillEntity bill;
}

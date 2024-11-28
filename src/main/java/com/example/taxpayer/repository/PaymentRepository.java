package com.example.taxpayer.repository;

import com.example.taxpayer.api.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;


public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    @Query("SELECT p FROM PaymentEntity p WHERE (:userId IS NULL OR p.user.id = :userId) " +
            "AND (:minAmount IS NULL OR p.paymentAmount >= :minAmount) " +
            "AND (:maxAmount IS NULL OR p.paymentAmount <= :maxAmount) " +
            "AND (:dueDate IS NULL OR p.paymentDate = :dueDate)")
    Page<PaymentEntity> findPayments(Long userId, Double minAmount, Double maxAmount, String dueDate, Pageable pageable);
}

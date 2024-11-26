package com.example.taxpayer.repository;

import com.example.taxpayer.api.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<BillEntity, Long> {
}

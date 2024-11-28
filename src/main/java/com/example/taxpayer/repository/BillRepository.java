package com.example.taxpayer.repository;

import com.example.taxpayer.api.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BillRepository extends JpaRepository<BillEntity, Long>, JpaSpecificationExecutor<BillEntity> {
}

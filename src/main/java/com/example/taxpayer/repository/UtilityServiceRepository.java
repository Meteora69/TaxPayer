package com.example.taxpayer.repository;

import com.example.taxpayer.api.entity.UtilityServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilityServiceRepository extends JpaRepository<UtilityServiceEntity, Long> {
}

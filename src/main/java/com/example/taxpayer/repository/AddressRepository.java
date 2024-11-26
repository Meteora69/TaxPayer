package com.example.taxpayer.repository;

import com.example.taxpayer.api.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long>{
}

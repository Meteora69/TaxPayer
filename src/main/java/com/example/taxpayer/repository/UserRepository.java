package com.example.taxpayer.repository;

import com.example.taxpayer.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
}

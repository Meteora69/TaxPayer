package com.example.taxpayer.repository;

import com.example.taxpayer.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}

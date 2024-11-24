package com.example.taxpayer.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utility_services")
public class UtilityServiceEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "is_fixed", nullable = false)
        private boolean isFixed;

        @Column(name = "tariff", nullable = false)
        private BigDecimal tariff;

        @Column(name = "cycle", nullable = false)
        private String cycle;

}

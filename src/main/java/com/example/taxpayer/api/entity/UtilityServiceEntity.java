package com.example.taxpayer.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.taxpayer.api.enums.UtilityService;

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

        @Enumerated(EnumType.STRING) // Зберігає назву enum у вигляді тексту в базі даних
        @Column(name = "name", nullable = false)
        private UtilityService name;

        @Column(name = "is_fixed", nullable = false)
        private Boolean isFixed;

        @Column(name = "tariff", nullable = false)
        private BigDecimal tariff;

        @Column(name = "cycle", nullable = false)
        private String cycle;

}

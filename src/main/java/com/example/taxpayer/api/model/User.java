package com.example.taxpayer.api.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

   @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "balance", nullable = false)
    private Double balance;



}

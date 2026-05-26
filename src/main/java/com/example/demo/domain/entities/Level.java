package com.example.demo.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Category category;

    @Column(columnDefinition = "TEXT")
    private String starterCode;
}

enum Category {
    SECUENCIALES,
    CONDICIONALES,
    BUCLES,
    ESTRUCTURAS_DE_DATOS,
    STREAMS
}

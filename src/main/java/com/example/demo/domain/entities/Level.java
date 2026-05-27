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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getStarterCode() {
		return starterCode;
	}

	public void setStarterCode(String starterCode) {
		this.starterCode = starterCode;
	}
}

enum Category {
    SECUENCIALES,
    CONDICIONALES,
    BUCLES,
    ESTRUCTURAS_DE_DATOS,
    STREAMS
}

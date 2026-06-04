package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entities.Category;
import com.example.demo.data.LevelRepository;
import com.example.demo.domain.entities.Level;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LevelService {
	
	private LevelRepository levelRepository;
	
	public LevelService(LevelRepository levelRepository) {
		super();
		this.levelRepository = levelRepository;
	}

	public Level getLevelById(Long idLevel) {
		return levelRepository.findById(idLevel)
				.orElseThrow(() -> new EntityNotFoundException("No existe ningun level con este ID"));
	}
	
	public List<Level> getAllLevels(){
		return levelRepository.findAll();
	}

	public List<Level> getLevelsByCategory(Category category) {
		return levelRepository.findByCategory(category);
	}
	
	
}

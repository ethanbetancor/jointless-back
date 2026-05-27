package com.example.demo.domain.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.data.LevelRepository;
import com.example.demo.data.SolutionRepository;
import com.example.demo.data.TestRepository;
import com.example.demo.data.UserRepository;

@Service
public class SolutionService {
    
    public boolean isLevelPassedByUser(Long idLevel , Long idUser) {
    		return solutionRepository.existsByLevelIdAndUserIdAndPassedTrue(idLevel, idUser);
    }
}

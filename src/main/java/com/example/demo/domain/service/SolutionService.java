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
    private final TestRepository testRepository;
    private final SolutionRepository solutionRepository;
    private final LevelRepository levelRepository;
    private final UserRepository userRepository;

    public SolutionService(TestRepository testRepository, SolutionRepository solutionRepository, LevelRepository levelRepository, UserRepository userRepository) {

        this.testRepository = testRepository;
        this.solutionRepository = solutionRepository;
        this.levelRepository = levelRepository;
        this.userRepository = userRepository;
    }


    private static String extractTestFailure(String output) {
        return Arrays.stream(output.split("\n"))
                .filter(line -> line.contains("expected:"))
                .findFirst()
                .map(line -> line.replaceAll(".*expected:", "expected:").trim())
                .orElse("test fallido");
    }
    
    public boolean isLevelPassedByUser(Long idLevel , Long idUser) {
    		return solutionRepository.existsByLevelIdAndUserIdAndPassedTrue(idLevel, idUser);
    }
}

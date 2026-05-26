package com.example.demo.domain.security;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SolutionManager {
	
	private Path path;
	private LevelRepository levelRepository;
	
	public SolutionManager(LevelRepository levelRepository) {
		this.levelRepository=levelRepository;
	}

	//@PostConstruct //preguntar a david
	private void generateTemporalPath() throws IOException {
		try {
			path = Files.createTempDirectory("solution-");
		} catch (IOException e) {
			throw new IOException("Error in the process of creating the temporal path");

		}
	}
	
	private boolean generateTemporalSolutionCode(String code) throws IOException {
		if(code==null || code.isBlank()) return false;
		try {
			Files.writeString(path.resolve("Solution.java"), code);
			return true;
		} catch (IOException e) {
			throw new IOException("Error in the process of creating the Solution.java in the temporal path");
		}
	}
	
	private boolean generateTemporalSolutionTest(Long testID) throws IOException {
		if(testID==null) return false;
		try {
			Files.writeString(path.resolve("SolutionTest.java"), Files.readString(Paths.get(levelRepository.findById(testID).orElseThrow().getTestPath())));
			return true;
		} catch (IOException e) {
			throw new IOException("Error in the process of creating the Solution.java in the temporal path");
		}

		return true;
	}

	public boolean generateAll(String code, Long testID) throws IOException {
		this.generateTemporalPath();
        return this.generateTemporalSolutionTest(testID) && generateTemporalSolutionCode(code);
    }

	public Path getPath() {
		return path;
	}
}

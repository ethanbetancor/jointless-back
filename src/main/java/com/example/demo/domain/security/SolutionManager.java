package com.example.demo.domain.security;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.demo.data.TestRepository;
import org.springframework.stereotype.Component;

import com.example.demo.data.LevelRepository;

@Component
public class SolutionManager {

	private final TestRepository testRepository;
	private final LevelRepository levelRepository;

	public SolutionManager(LevelRepository levelRepository, TestRepository testRepository) {
		this.levelRepository = levelRepository;
		this.testRepository = testRepository;
	}

	private Path generateTemporalPath() throws IOException {
		return Files.createTempDirectory("solution-");
	}

	private void generateTemporalSolutionCode(Path path, String code) throws IOException {
		if (code == null || code.isBlank()) throw new IOException("El código no puede estar vacío");
		Files.writeString(path.resolve("Solution.java"), code);
	}

	private void generateTemporalSolutionTest(Path path, Long testID) throws IOException {
		if (testID == null) throw new IOException("El testID no puede ser nulo");
		String testPath = testRepository.findById(testID).orElseThrow().getTestPath();
		Files.copy(Paths.get(testPath), path.resolve("SolutionTest.java"));
	}

	public Path generateAll(String code, Long testID) throws IOException {
		Path tempDir = generateTemporalPath();
		generateTemporalSolutionCode(tempDir, code);
		generateTemporalSolutionTest(tempDir, testID);
		return tempDir;
	}
}

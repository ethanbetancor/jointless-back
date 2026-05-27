package com.example.demo.domain.service;

import com.example.demo.data.LevelRepository;
import com.example.demo.data.SolutionRepository;
import com.example.demo.data.TestRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.domain.entities.Level;
import com.example.demo.domain.entities.Solution;
import com.example.demo.domain.entities.Test;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.service.result.DockerResult;
import com.example.demo.ui.dtos.solution.SubmitResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class SolutionService {

    private final DockerService dockerService;
    private final TestRepository testRepository;
    private final SolutionRepository solutionRepository;
    private final LevelRepository levelRepository;
    private final UserRepository userRepository;

    public SolutionService(DockerService dockerService, TestRepository testRepository, SolutionRepository solutionRepository, LevelRepository levelRepository, UserRepository userRepository) {
        this.dockerService = dockerService;
        this.testRepository = testRepository;
        this.solutionRepository = solutionRepository;
        this.levelRepository = levelRepository;
        this.userRepository = userRepository;
    }

    public SubmitResponse submit(String code, Long levelId, Long userId) {
        Test test = testRepository.findByLevelId(levelId);
        DockerResult result = dockerService.runInContainer(code, test.getId());
        boolean passed = result.getExitCode() == 0;
        if(passed) {
        	Level level = levelRepository.findById(levelId).orElseThrow();
            User user = userRepository.findById(userId).orElseThrow();
            Solution solution = new Solution(level, user, code, passed);
            solutionRepository.save(solution);
        }

        return parseOutput(result.getOutput(), result.getExitCode());
    }

    private static SubmitResponse parseOutput(String rawOutput, int exitCode) {
        String output = rawOutput.replaceAll("\u001B\\[[;\\d]*m", "");

        if (exitCode == 0) {
            return new SubmitResponse("todos los tests pasaron correctamente" , true);
        }

        if (output.contains("error:")) {
            return new SubmitResponse(extractCompileError(output), false);
        }

        if (output.contains("AssertionFailedError")) {
            return new SubmitResponse(extractTestFailure(output), false);
        }

        return new SubmitResponse(output,false);
    }

    private static String extractCompileError(String output) {
        return Arrays.stream(output.split("\n"))
                .filter(line -> line.contains("error:"))
                .collect(Collectors.joining("\n"));
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

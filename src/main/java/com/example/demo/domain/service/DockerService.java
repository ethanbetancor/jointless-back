package com.example.demo.domain.service;

import com.example.demo.domain.security.SolutionManager;
import com.example.demo.domain.service.result.DockerResult;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Service
public class DockerService {

    private final SolutionManager solutionManager;

    private static final String IMAGE_NAME = "java-sandbox";
    private static final int TIMEOUT_SECONDS = 16;

    public DockerService(SolutionManager solutionManager) {
        this.solutionManager = solutionManager;
    }

    public DockerResult runInContainer(String userCode, Long testId) {
        Path tempDir = null;
        try {
            solutionManager.generateAll(userCode, testId);

            ProcessBuilder pb = new ProcessBuilder(
                    "docker", "run", "--rm",
                    "--memory", "256m",
                    "--cpus", "0.5",
                    "--network", "none",
                    "--pids-limit", "50",
                    "-v", tempDir.toAbsolutePath() + ":/sandbox",
                    IMAGE_NAME,
                    "/bin/sh", "-c",
                    "javac -cp /opt/junit.jar Solution.java SolutionTest.java 2>&1 && " +
                            "java -jar /opt/junit.jar --class-path . --select-class=SolutionTest 2>&1"
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            String output = new String(process.getInputStream().readAllBytes());

            boolean finished = process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return DockerResult.timeout();
            }
            FileUtils.deleteDirectory(tempDir.toFile());
            return DockerResult.create(output, process.exitValue());

        } catch (InterruptedException | IOException e) {
            return DockerResult.error(e.getMessage());
        }
    }
}

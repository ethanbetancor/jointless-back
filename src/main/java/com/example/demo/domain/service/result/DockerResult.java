package com.example.demo.domain.service.result;

public class DockerResult {
    private final String output;
    private final int exitCode;
    private final boolean timedOut;
    private final boolean error;

    private DockerResult(String output, int exitCode, boolean timedOut, boolean error) {
        this.output = output;
        this.exitCode = exitCode;
        this.timedOut = timedOut;
        this.error = error;
    }


    public static DockerResult create(String output, int exitCode) {
        return new DockerResult(output, exitCode, false, false);
    }

    public static DockerResult timeout() {
        return new DockerResult("Tiempo límite superado (16s)", -1, true, false);
    }

    public static DockerResult error(String message) {
        return new DockerResult("Error interno: " + message, -1, false, true);
    }

    public String getOutput() {
        return output;
    }

    public int getExitCode() {
        return exitCode;
    }
}

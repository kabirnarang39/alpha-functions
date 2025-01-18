package com.alpha.functions.factories;

import com.alpha.functions.entities.AlphaFunction;
import com.alpha.functions.utils.constants.ApplicationConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Factory class for creating processes for building and pushing Docker containers.
 */
public class ProcessFactory {


    /**
     * Creates a process to build a Docker container.
     *
     * @param alphaFunction  the {@link AlphaFunction} entity containing the function details.
     * @param dockerfilePath the path to the Dockerfile.
     * @param scriptPath     the path to the build script.
     * @return a {@link Process} to execute the build.
     * @throws IllegalArgumentException if any argument is invalid.
     * @throws IOException              if an I/O error occurs.
     */
    public static Process getBuildProcess(AlphaFunction alphaFunction, Path dockerfilePath, String scriptPath) throws IOException {
        validateInputs(alphaFunction, dockerfilePath, scriptPath);

        String[] command = {
                ApplicationConstants.BASH_COMMAND,
                scriptPath,
                ApplicationConstants.ARG_NAME, alphaFunction.getAlphaName().toLowerCase(),
                ApplicationConstants.ARG_DIRECTORY, dockerfilePath.getParent().toString(),
                ApplicationConstants.ARG_TAG, ApplicationConstants.TAG_LATEST,
                ApplicationConstants.ARG_PATH, dockerfilePath.getParent().toString(),
                ApplicationConstants.ARG_FILE, dockerfilePath.getFileName().toString()
        };

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        return processBuilder.start();
    }

    /**
     * Creates a process to push a Docker container to a repository.
     *
     * @param alphaFunction the {@link AlphaFunction} entity containing the function details.
     * @param scriptPath    the path to the push script.
     * @return a {@link Process} to execute the push.
     * @throws IllegalArgumentException if any argument is invalid.
     * @throws IOException              if an I/O error occurs.
     */
    public static Process getPushProcess(AlphaFunction alphaFunction, String scriptPath) throws IOException {
        validateInputs(alphaFunction, null, scriptPath);

        String[] command = {
                ApplicationConstants.BASH_COMMAND,
                scriptPath,
                ApplicationConstants.ARG_USER, alphaFunction.getRepositoryUserName(),
                ApplicationConstants.ARG_IMAGE, alphaFunction.getAlphaName().toLowerCase(),
                ApplicationConstants.ARG_TAG, ApplicationConstants.TAG_LATEST
        };

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        return processBuilder.start();
    }

    /**
     * Validates the input arguments.
     *
     * @param alphaFunction  the {@link AlphaFunction} entity.
     * @param dockerfilePath the path to the Dockerfile (can be null for push process).
     * @param scriptPath     the path to the script.
     * @throws IllegalArgumentException if validation fails.
     */
    private static void validateInputs(AlphaFunction alphaFunction, Path dockerfilePath, String scriptPath) {
        if (alphaFunction == null) {
            throw new IllegalArgumentException("AlphaFunction cannot be null.");
        }
        if (dockerfilePath != null && (!Files.exists(dockerfilePath) || !Files.isRegularFile(dockerfilePath))) {
            throw new IllegalArgumentException("Invalid Dockerfile path: " + dockerfilePath);
        }
        if (scriptPath == null || scriptPath.isBlank()) {
            throw new IllegalArgumentException("Script path cannot be null or blank.");
        }
    }
}

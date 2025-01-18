package com.alpha.functions.factories;

import com.alpha.functions.entities.AlphaFunction;
import com.alpha.functions.entities.FileUtility;
import com.alpha.functions.utils.constants.ApplicationConstants;

import com.alpha.functions.utils.utilities.DockerfileConfigStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Component
public class DockerfileConfigFactory {

    @Autowired
    DockerfileConfigStrategy javaConfigStrategy;

    /**
     * Generates a configuration map for creating a Dockerfile based on the given AlphaFunction.
     *
     * @param alphaFunction the AlphaFunction object containing details to create the Dockerfile config
     * @return a Map containing the Dockerfile configuration parameters
     * @throws IllegalArgumentException if the provided language is unsupported
     */
    public Map<String, String> getConfig(AlphaFunction alphaFunction) throws JsonProcessingException {
        Path executablePath = extractExecutablePath(alphaFunction);
        return switch (alphaFunction.getLanguage().toLowerCase()) {
            case ApplicationConstants.JAVA_LANGUAGE -> javaConfigStrategy.createConfig(alphaFunction, executablePath);
            default -> throw new IllegalArgumentException("Unsupported language: " + alphaFunction.getLanguage());
        };
    }

    /**
     * Extracts the executable path from the AlphaFunction's file utilities.
     * Assumes that the list contains at least one element.
     *
     * @param alphaFunction the AlphaFunction object containing file utilities
     * @return the executable path
     */
    private Path extractExecutablePath(AlphaFunction alphaFunction) {
        List<FileUtility> fileUtilities = alphaFunction.getFileUtilities();
        if (fileUtilities.isEmpty()) {
            throw new IllegalArgumentException("No file utilities found for the AlphaFunction.");
        }
        return Paths.get(fileUtilities.getFirst().getFilePath());
    }
}

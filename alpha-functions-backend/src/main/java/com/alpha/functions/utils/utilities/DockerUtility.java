package com.alpha.functions.utils.utilities;

import com.alpha.functions.entities.AlphaFunction;
import com.alpha.functions.factories.DockerfileConfigFactory;
import com.alpha.functions.factories.ProcessFactory;
import com.alpha.functions.utils.enums.FileSystemPaths;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class DockerUtility {

    @Autowired
    DockerfileConfigFactory dockerfileConfigFactory;

    public void build(AlphaFunction alphaFunction) {
        Path path = null;
        try {

            String dockerFileContent = generateDockerfileContent(dockerfileConfigFactory.getConfig(alphaFunction));
            Path dockerfilePath = Paths.get(System.getProperty("user.dir"),"executables", "Dockerfile." + alphaFunction.getAlphaName().toLowerCase());
            path = Files.writeString(dockerfilePath, dockerFileContent);
            Process process = ProcessFactory.getBuildProcess(alphaFunction, dockerfilePath, FileSystemPaths.DOCKER_IMAGE_BUILD_SCRIPT_PATH.path);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (path != null && Files.exists(path)) {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    System.err.println("Error deleting Dockerfile: " + e.getMessage());
                }
            }
        }
    }

    private String generateDockerfileContent(Map<String, String> variables) throws IOException {
        String dockerfile = FileUtil.readAsString(Paths.get(FileSystemPaths.DOCKERFILE_TEMPLATE_PATH.path).toFile());
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            dockerfile = dockerfile.replace(placeholder, entry.getValue());
        }
        return dockerfile;
    }


    public void push(AlphaFunction alphaFunction) {
        try {
            Process process = ProcessFactory.getPushProcess(alphaFunction, FileSystemPaths.DOCKER_IMAGE_PUSH_SCRIPT_PATH.path);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.alpha.functions.utils.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtils {

    @Value("${file.upload-dir}")
    String uploadDir;

    public Path uploadFile(MultipartFile file, String physicalFileName) throws IOException {
        Path filePath = Paths.get(uploadDir, physicalFileName);
        Files.write(filePath, file.getBytes());
        return filePath;
    }

    public static String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        if (sb.isEmpty())
            return "{}";
        return sb.toString();
    }

}

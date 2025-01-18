package com.alpha.functions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Component
@RequestScope
public class AUploadRequest {
    @JsonIgnore
    private LocalDateTime requestStartTime;
    @JsonIgnore
    private LocalDateTime requestEndTime;
    @JsonIgnore
    private HttpServletRequest httpRequest;
    @JsonIgnore
    private HttpServletResponse httpResponse;
    private List<MultipartFile> alphaFunctionFiles;
    private Map<String, String> pathParams;
    private Map<String, String[]> queryParams;
    private String data;
    private String requestMethod;
}

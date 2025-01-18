package com.alpha.functions.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Data
public class AFunction {
    private Long alphaFunctionId;
    private Map<String,Object> data;
    private String alphaFunctionName;
    private Long alphaFunctionTimeout;
    private String alphaFunctionRuntime;
    private String alphaFunctionHandler;
    private String alphaFunctionDescription;
    private String alphaFunctionOperation;
    private String alphaFunctionLanguage;
    private Long version;
    private List<MultipartFile> alphaFunctionFiles;
    private String repositoryUserName;
    private int parallelReplicas;
    private String minimumCpu;
    private String maximumCpu;
    private String minimumMemory;
    private String maximumMemory;
    private String envVariables;
    private boolean parallelExecutionEnabled;
    private boolean isExecuted;
    private int maxRetries;
}

package com.alpha.functions.handlers.implementations;

import com.alpha.functions.dto.AFunction;
import com.alpha.functions.entities.AlphaFunction;
import com.alpha.functions.handlers.AFunctionExecutionLifecycle;
import com.alpha.functions.repositories.AlphaFunctionRepository;
import com.alpha.functions.utils.utilities.DockerUtility;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Component
public class AFunctionExecutionLifecycleHandler implements AFunctionExecutionLifecycle {


    @Autowired
    AlphaFunctionRepository alphaFunctionRepository;

    @Autowired
    DockerUtility dockerUtility;

    @Autowired
    KubernetesLifecycleHandler kubernetesLifecycleHandler;

    private static AlphaFunction alphaFunction = new AlphaFunction();

    private void preHandle(AFunction aFunction) {
        alphaFunction = alphaFunctionRepository.findByIdAndIsLatestVersion(aFunction.getAlphaFunctionId(), Boolean.TRUE);
        alphaFunction.setData(aFunction.getData());
        if (!alphaFunction.isExecuted()) {
            alphaFunction.setLastExecutionStartDateTime(LocalDateTime.now());
        }
    }

    public AFunction execute(AFunction aFunction) {
        preHandle(aFunction);
        if (!alphaFunction.isExecuted()) {
            build();
            push();
        }
        run();
        postHandle();
        return aFunction;
    }

    @Override
    public void build() {
        dockerUtility.build(alphaFunction);
    }

    @Override
    public void push() {
        dockerUtility.push(alphaFunction);
    }

    @Override
    public void run() {
        kubernetesLifecycleHandler.handleKubernetesProcess(alphaFunction);
    }

    private void postHandle() {
        alphaFunction.setExecuted(true);
        alphaFunction.setLastExecutionEndDateTime(LocalDateTime.now());
        alphaFunctionRepository.save(alphaFunction);
    }
}

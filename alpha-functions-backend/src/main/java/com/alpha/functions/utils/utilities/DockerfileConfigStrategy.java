package com.alpha.functions.utils.utilities;

import com.alpha.functions.entities.AlphaFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.nio.file.Path;
import java.util.Map;

public interface DockerfileConfigStrategy {
    Map<String, String> createConfig(AlphaFunction alphaFunction, Path executablePath) throws JsonProcessingException;
}

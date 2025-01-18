package com.alpha.functions.utils.utilities;

import com.alpha.functions.dto.ARequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonUtils {

    @Autowired
    ObjectMapper objectMapper;

    public <T> T convertStringToClass(String requestBody, Class<T> tClass) throws IOException {
        return objectMapper.readValue(requestBody, tClass);
    }

    public String convertToJsonString(Object requestBody) throws JsonProcessingException {
        return objectMapper.writeValueAsString(requestBody);
    }
}

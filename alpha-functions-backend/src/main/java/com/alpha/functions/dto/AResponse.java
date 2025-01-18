package com.alpha.functions.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class AResponse {
    String message;
    boolean hasError;
    long code;
    Map<String, Object> data;
}

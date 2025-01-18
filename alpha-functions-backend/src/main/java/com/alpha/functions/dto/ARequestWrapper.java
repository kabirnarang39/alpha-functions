package com.alpha.functions.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class ARequestWrapper {
    private ARequest aRequest;
    private AUploadRequest aUploadRequest;
    private String alphaFunctionOperation;
}

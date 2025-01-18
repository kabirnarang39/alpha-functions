package com.alpha.functions.services;

import com.alpha.functions.dto.AResponse;
import com.alpha.functions.handlers.implementations.OperationsHandler;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class OperationsService {
    private final OperationsHandler operationsHandler;

    public AResponse performOperation() {
        return operationsHandler.handleRequest();
    }
}

package com.alpha.functions.controllers;

import com.alpha.functions.dto.ARequestWrapper;
import com.alpha.functions.dto.AResponse;
import com.alpha.functions.services.OperationsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alpha/function")
@Data
@CrossOrigin(origins = "http://localhost:3000")
public class OperationsController {

    private final OperationsService operationsService;

    @Autowired
    ARequestWrapper requestWrapper;

    @RequestMapping(value = "/{operation}", method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE}, consumes = {MediaType.ALL_VALUE})
    public AResponse handleCrudOperation(@PathVariable String operation) {
        requestWrapper.setAlphaFunctionOperation(operation);
        return operationsService.performOperation();
    }
}

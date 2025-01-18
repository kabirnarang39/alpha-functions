package com.alpha.functions.utils.enums;

import org.springframework.http.HttpStatus;

public enum Response {
    SUCCESS("Request Successful", HttpStatus.OK.value()),
    INTERNAL_SERVER_ERROR("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    INVALID_OPERATION("Invalid operation", HttpStatus.INTERNAL_SERVER_ERROR.value());

    public final String message;
    public final long code;

    Response(String message, long code) {
        this.message = message;
        this.code = code;
    }
}

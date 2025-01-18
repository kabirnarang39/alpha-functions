package com.alpha.functions.utils.enums;

public enum ErrorMessages {
    INVALID_HANDLER("Class or handler not found::"),
    NO_HANDLER_DEFINED("Class and handler not defined."),
    ALPHA_FUNCTION_TIMEOUT("Alpha function time out. Max timeout is 15m and defaults to 90s if not provided. Either increase timeout or make sure function is executed within specified time out value. Process exited within: ");
    public final String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

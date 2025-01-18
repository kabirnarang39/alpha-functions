package com.alpha.functions.utils.enums;

public enum ApplicationDefaults {
    ALPHA_FUNCTION_HANDLER_NAME("handleRequest"),
    ALPHA_FUNCTION_INITIAL_PAYLOAD(System.getenv().getOrDefault("ENTRY_PAYLOAD", null));
    public final String value;

    ApplicationDefaults(String value) {
        this.value = value;
    }
}

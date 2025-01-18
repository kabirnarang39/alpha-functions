package com.alpha.functions.utils.enums;

public enum ApplicationDefaults {
    ALPHA_FUNCTION_TIMEOUT("ALPHA_FUNCTION_TIMEOUT", "90"),
    ALPHA_FUNCTION_HANDLER_NAME("ALPHA_FUNCTION_HANDLER_NAME", "handleRequest"),
    ALPHA_FUNCTION_INITIAL_PAYLOAD("ENTRY_PAYLOAD", System.getenv().getOrDefault("ENTRY_PAYLOAD", null));
    public final String key;
    public final String value;

    ApplicationDefaults(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

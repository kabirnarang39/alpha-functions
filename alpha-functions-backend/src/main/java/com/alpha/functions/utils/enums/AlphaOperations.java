package com.alpha.functions.utils.enums;

public enum AlphaOperations {
    CREATE("create"),
    EXECUTE("execute"),
    READ("read");
    public final String operation;

    AlphaOperations(String operation) {
        this.operation = operation;
    }
}

package com.alpha.functions.handlers;


public interface AFunctionExecutionLifecycle {
    void build();

    void push();

    void run();
}

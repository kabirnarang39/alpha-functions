package com.alpha.functions.handlers;

public interface ApplicationLifecycle {
    public void start(String argument) throws Exception;
    public void exit(Boolean timeout) throws Exception;
}

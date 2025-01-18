package com.alpha.functions.handlers;

public abstract class BaseHandler<R, T> {

    private void preHandle(R userRequest) {
    }

    public T handleRequest(R request) {
        preHandle(request);
        T response = execute(request);
        postHandle(response);
        return response;
    }

    private void postHandle(T response) {
    }

    public abstract T execute(R request);
}
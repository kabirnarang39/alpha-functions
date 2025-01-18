package com.alpha.functions.handlers;

public abstract class BaseHandler<R, T> {

    private void preHandle(R userRequest) {
        System.out.println("preHandle");
    }

    public T handleRequest(R request) {
        System.out.println("handleRequest");
        preHandle(request);
        T response = execute(request);
        postHandle(response);
        return response;
    }

    private void postHandle(T response) {
        System.out.println("postHandle");
    }

    public abstract T execute(R request);
}
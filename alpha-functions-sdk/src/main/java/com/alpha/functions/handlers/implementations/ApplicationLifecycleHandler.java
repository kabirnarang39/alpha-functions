package com.alpha.functions.handlers.implementations;

import com.alpha.functions.handlers.ApplicationLifecycle;
import com.alpha.functions.invokation.HandleInvocation;

import java.util.concurrent.*;
import java.util.function.Supplier;


public class ApplicationLifecycleHandler implements ApplicationLifecycle {

    /**
     * Executes a task with a timeout and handles exceptions.
     *
     * @param argument the argument to be passed to the invoked handler
     */
    @Override
    public void start(String argument) throws Exception {
        CompletableFuture<Object> future = null;
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            Supplier<Object> task = createHandlerTask(argument);
            future = CompletableFuture.supplyAsync(task, executorService);
            future.get();
        }
    }

    /**
     * Creates a callable task that handles the provided argument.
     *
     * @param argument the argument to be passed to the invocation handler
     * @return the callable task
     */
    private static Supplier<Object> createHandlerTask(String argument) {
        return () -> {
            try {
                return HandleInvocation.handleInvocation(argument);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        };
    }

    @Override
    public void exit(Boolean timeout) {
    }
}

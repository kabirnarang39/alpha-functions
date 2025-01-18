package com.alpha.functions.handlers.implementations;

import com.alpha.functions.handlers.ApplicationLifecycle;
import com.alpha.functions.invokation.HandleInvocation;
import com.alpha.functions.utils.enums.ApplicationDefaults;
import com.alpha.functions.utils.enums.ErrorMessages;
import com.alpha.functions.utils.exceptions.AException;

import java.util.concurrent.*;
import java.util.function.Supplier;

import static com.alpha.functions.utils.enums.ApplicationDefaults.ALPHA_FUNCTION_TIMEOUT;

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
            Object futureResponse = future.get();
            System.out.println("Result " + futureResponse);
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

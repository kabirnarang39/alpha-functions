package com.alpha.functions;

import com.alpha.functions.handlers.ApplicationLifecycle;
import com.alpha.functions.handlers.implementations.ApplicationLifecycleHandler;
import com.alpha.functions.utils.enums.ErrorMessages;
import com.alpha.functions.utils.exceptions.AException;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new AException(ErrorMessages.NO_HANDLER_DEFINED.errorMessage);
        }
        ApplicationLifecycle applicationLifecycle = new ApplicationLifecycleHandler();
        applicationLifecycle.start(args[0]);
    }
}
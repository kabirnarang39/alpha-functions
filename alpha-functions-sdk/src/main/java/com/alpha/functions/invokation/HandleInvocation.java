package com.alpha.functions.invokation;

import com.alpha.functions.utils.enums.ApplicationDefaults;
import com.alpha.functions.utils.enums.ErrorMessages;
import com.alpha.functions.utils.exceptions.AException;

import java.lang.reflect.Method;

public class HandleInvocation {

    public static Object handleInvocation(String handler) throws AException {
        try {
            String[] path = handler.split("::");
            String className = path[0];
            String methodName = ApplicationDefaults.ALPHA_FUNCTION_HANDLER_NAME.value;
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Method method = clazz.getMethod(methodName, Object.class);
            return method.invoke(instance, ApplicationDefaults.ALPHA_FUNCTION_INITIAL_PAYLOAD.value);
        } catch (Exception e) {
            throw new AException(ErrorMessages.INVALID_HANDLER.errorMessage + handler);
        }
    }
}

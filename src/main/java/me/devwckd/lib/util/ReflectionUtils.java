package me.devwckd.lib.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author devwckd
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionUtils {

    public static Constructor<?>[] getConstructors(Class<?> clazz) {
        return clazz.getConstructors();
    }

    public static Constructor<?> getFirstConstructor(Class<?> clazz) {
        return getConstructors(clazz)[0];
    }

    public static boolean isFirstConstructorEmpty(Class<?> clazz) {
        return getFirstConstructor(clazz).getParameterTypes().length < 1;
    }

    public static Class<?>[] getConstructorParameterTypes(Constructor<?> constructor) {
        return constructor.getParameterTypes();
    }

    public static Parameter[] getConstructorParameters(Constructor<?> constructor) {
        return constructor.getParameters();
    }

    public static Object newInstance(Constructor<?> constructor, Object... params) {
        try {
            return constructor.newInstance(params);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    public static Object invokeMethodAndReturnResult(Method method, Object object, Object... params) {
        try {
            method.setAccessible(true);
            return method.invoke(object, params);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

}

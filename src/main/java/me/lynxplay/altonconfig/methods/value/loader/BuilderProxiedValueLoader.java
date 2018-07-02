package me.lynxplay.altonconfig.methods.value.loader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BuilderProxiedValueLoader implements ProxiedValueLoader {

    private Method method;
    private Method builder;

    public BuilderProxiedValueLoader(Method method, Method builder) {
        this.method = method;
        this.builder = builder;

        this.builder.setAccessible(true);
    }

    /**
     * Returns the value of the current object
     *
     * @param proxInstance the proxy instance
     * @param value        the value to load
     * @return the value
     */
    @Override
    public Object getValue(Object proxInstance, String value) {
        try {
            return builder.invoke(proxInstance, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

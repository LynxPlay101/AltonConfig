package me.lynxplay.altonconfig.methods.value.loader;

import java.lang.reflect.Method;

public class SimpleProxiedValueLoader implements ProxiedValueLoader {

    private final Method method;

    public SimpleProxiedValueLoader(Method method) {
        this.method = method;
    }

    /**
     * Returns the value of the current object
     *
     * @param configInstance the proxy instance
     * @param value          the value to load
     * @return the value
     */
    @Override
    public Object getValue(Object configInstance, String value) {
        Class<?> returnType = this.method.getReturnType();

        if (byte.class.isAssignableFrom(returnType)) {
            return (Byte.parseByte(value));
        } else if (short.class.isAssignableFrom(returnType)) {
            return (Short.parseShort(value));
        } else if (int.class.isAssignableFrom(returnType)) {
            return (Integer.parseInt(value));
        } else if (long.class.isAssignableFrom(returnType)) {
            return (Long.parseLong(value));
        } else if (float.class.isAssignableFrom(returnType)) {
            return (Float.parseFloat(value));
        } else if (double.class.isAssignableFrom(returnType)) {
            return (Double.parseDouble(value));
        } else if (boolean.class.isAssignableFrom(returnType)) {
            return (Boolean.parseBoolean(value));
        } else if (char.class.isAssignableFrom(returnType)) {
            return (value.charAt(0));
        } else if (String.class.isAssignableFrom(returnType)) {
            return (value);
        } else if (Enum.class.isAssignableFrom(returnType)) {
            return (Enum.valueOf((Class<? extends Enum>) returnType, value));
        } else {
            throw new RuntimeException("Could not parse input " + value);
        }
    }

}

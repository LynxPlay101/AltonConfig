package me.lynxplay.altonconfig.methods.value.transformer;

public class DefaultProxiedValueTransformer<T> implements ProxiedValueTransformer<T> {
    /**
     * Transforms the value based on the arguments passed
     *
     * @param value
     * @param arguments the arguments
     * @return the transformed value
     */
    @Override
    public T transform(T value, Object... arguments) {
        return value;
    }
}

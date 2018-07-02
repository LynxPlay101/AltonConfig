package me.lynxplay.altonconfig.methods.value.transformer;

public interface ProxiedValueTransformer<T> {

    /**
     * Transforms the value based on the arguments passed
     *
     * @param arguments the arguments
     * @return the transformed value
     */
    T transform(T value, Object... arguments);

}

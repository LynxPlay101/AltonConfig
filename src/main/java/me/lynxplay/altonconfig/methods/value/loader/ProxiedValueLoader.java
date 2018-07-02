package me.lynxplay.altonconfig.methods.value.loader;

public interface ProxiedValueLoader {

    /**
     * Returns the value of the current object
     *
     * @param configInstance the proxy instance
     * @param value          the value to load
     * @return the value
     */
    Object getValue(Object configInstance, String value);

}

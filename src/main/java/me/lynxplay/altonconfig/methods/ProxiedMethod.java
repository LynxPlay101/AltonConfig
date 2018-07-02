package me.lynxplay.altonconfig.methods;

import me.lynxplay.altonconfig.annotation.method.DefaultValue;
import me.lynxplay.altonconfig.annotation.method.Value;
import me.lynxplay.altonconfig.exceptions.NotProxiedMethodException;
import me.lynxplay.altonconfig.methods.value.loader.ProxiedValueLoader;
import me.lynxplay.altonconfig.methods.value.transformer.ProxiedValueTransformer;
import me.lynxplay.altonconfig.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Supplier;

public class ProxiedMethod {

    private static final Supplier<NotProxiedMethodException> NOT_PROXIED_METHOD_EXCEPTION_SUPPLIER = () -> new NotProxiedMethodException("The given method does not have a ConfigValue annotaion");

    private Method method;
    private ProxiedValueLoader loader;
    private ProxiedValueTransformer transformer;

    public ProxiedMethod(Method method, ProxiedValueLoader loader, ProxiedValueTransformer transformer) {
        this.method = method;
        this.loader = loader;
        this.transformer = transformer;
    }

    /**
     * Returns the config value
     *
     * @return the config value
     */
    public Value getConfigValue() {
        return Optional.ofNullable(method.getAnnotation(Value.class))
                .orElseThrow(NOT_PROXIED_METHOD_EXCEPTION_SUPPLIER);
    }

    public Optional<DefaultValue> getDefault() {
        return Optional.ofNullable(method.getAnnotation(DefaultValue.class));
    }

    /**
     * Returns the method this proxied method is based on
     *
     * @return the method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Loads the objects value
     *
     * @param value         the value
     * @param proxyInstance the proxy instance
     * @return the loaded value
     */
    public Object load(Object proxyInstance, String value) {
        return this.loader.getValue(proxyInstance, value);
    }

    /**
     * Transforms a value by the provided arguments
     *
     * @param value     the value
     * @param arguments the arguments
     * @return the transformed value
     */
    public Object transform(Object value, Object... arguments) {
        return this.transformer.transform(value, arguments);
    }

    /**
     * Returns the slot this method is stored under
     *
     * @return the method slot
     */
    public int getSlot() {
        return ReflectionUtil.getMethodSlot(getMethod());
    }
}

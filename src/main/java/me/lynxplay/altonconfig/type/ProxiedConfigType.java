package me.lynxplay.altonconfig.type;

import me.lynxplay.altonconfig.AltonConfig;
import me.lynxplay.altonconfig.annotation.ConfigAutoCreate;
import me.lynxplay.altonconfig.methods.ProxiedMethod;
import me.lynxplay.altonconfig.type.invokation.InvokationHandlerFactory;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ProxiedConfigType<T extends AltonConfig> {

    private final InvokationHandlerFactory handlerFactory;
    private Class<? extends AltonConfig> configInterface;
    private Map<Integer, ProxiedMethod> methods;

    public ProxiedConfigType(Class<T> configInterface, Map<Integer, ProxiedMethod> methods) {
        this.configInterface = configInterface;
        this.methods = methods;
        this.handlerFactory = new InvokationHandlerFactory(this);
    }

    /**
     * Builds a new warpper instance
     *
     * @return the instance
     */
    public T newInstance() {
        return (T) Proxy.newProxyInstance(configInterface.getClassLoader(), new Class[]{configInterface}, handlerFactory.newInvokationHandler());
    }

    /**
     * Returns the method registered at the given slot
     *
     * @param slot the slot
     * @return the method
     */
    public ProxiedMethod getMethod(int slot) {
        return methods.get(slot);
    }

    /**
     * Returns the list of methods
     *
     * @return the list
     */
    public Collection<ProxiedMethod> getMethods() {
        return methods.values();
    }

    /**
     * Returns the config interface this proxied type is wrapping
     *
     * @return the type
     */
    public Class<? extends AltonConfig> getConfigInterface() {
        return configInterface;
    }

    /**
     * Returns the annotation if present
     *
     * @return the annotation
     */
    public Optional<ConfigAutoCreate> isAutoCreate() {
        return Optional.ofNullable(getConfigInterface().getAnnotation(ConfigAutoCreate.class));
    }

}

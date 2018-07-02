package me.lynxplay.altonconfig.type.invokation;

import me.lynxplay.altonconfig.type.ProxiedConfigType;
import me.lynxplay.altonconfig.type.instance.ProxiedAltonConfig;

import java.lang.reflect.InvocationHandler;

public class InvokationHandlerFactory {

    private ProxiedConfigType type;

    public InvokationHandlerFactory(ProxiedConfigType type) {
        this.type = type;
    }

    /**
     * Builds a new invokation handler
     *
     * @return the invokation handler
     */
    public InvocationHandler newInvokationHandler() {
        ProxiedAltonConfig config = new ProxiedAltonConfig(this.type);
        return new AltonConfigInvokationHandler<>(config);
    }
}

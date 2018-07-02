package me.lynxplay.altonconfig.type.invokation;

import me.lynxplay.altonconfig.AltonConfig;
import me.lynxplay.altonconfig.methods.ProxiedMethod;
import me.lynxplay.altonconfig.type.instance.ProxiedAltonConfig;
import me.lynxplay.altonconfig.util.ReflectionUtil;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

public class AltonConfigInvokationHandler<T extends AltonConfig> implements InvocationHandler {

    private ProxiedAltonConfig<T> config;
    private MethodHandles.Lookup lookup;

    public AltonConfigInvokationHandler(ProxiedAltonConfig<T> config) {
        this.config = config;

        try {
            Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class);
            constructor.setAccessible(true);
            this.lookup = constructor.newInstance(config.getTypeWrapper().getConfigInterface()).in(config.getTypeWrapper().getConfigInterface());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isDefault()) { //Execute default methods without proxy interference
            final Class<?> declaringClass = method.getDeclaringClass();
            return lookup.unreflectSpecial(method, declaringClass)
                    .bindTo(proxy)
                    .invokeWithArguments(args);
        }

        if (Objects.equals(config.getTypeWrapper().getConfigInterface(), method.getDeclaringClass())) { //Method called in config interface
            int methodSlot = ReflectionUtil.getMethodSlot(method);

            ProxiedMethod proxiedMethod = config.getTypeWrapper().getMethod(methodSlot);
            return proxiedMethod.transform(config.getValue(methodSlot), args);
        } else if (Objects.equals(AltonConfig.class, method.getDeclaringClass())) { //Method called in alton super interface
            return config.getAltonSuperConfigTypeWrapper().execute(proxy, method, args);
        } else {
            return config.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(config, args); //Object methods
        }
    }

}

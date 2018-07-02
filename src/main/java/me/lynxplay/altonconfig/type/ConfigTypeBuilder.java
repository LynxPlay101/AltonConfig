package me.lynxplay.altonconfig.type;

import me.lynxplay.altonconfig.AltonConfig;
import me.lynxplay.altonconfig.annotation.method.Builder;
import me.lynxplay.altonconfig.annotation.method.Value;
import me.lynxplay.altonconfig.methods.ProxiedMethod;
import me.lynxplay.altonconfig.methods.value.loader.BuilderProxiedValueLoader;
import me.lynxplay.altonconfig.methods.value.loader.ProxiedValueLoader;
import me.lynxplay.altonconfig.methods.value.loader.SimpleProxiedValueLoader;
import me.lynxplay.altonconfig.methods.value.transformer.DefaultProxiedValueTransformer;
import me.lynxplay.altonconfig.methods.value.transformer.PlaceholderProxiedValueTransformer;
import me.lynxplay.altonconfig.methods.value.transformer.ProxiedValueTransformer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigTypeBuilder {

    private Map<Class<? extends AltonConfig>, ProxiedConfigType> loadedTypes = new IdentityHashMap<>();

    /**
     * Loads a type wrapper
     *
     * @param configClass the config type class
     * @return the type
     */
    public <T extends AltonConfig> ProxiedConfigType<T> load(Class<T> configClass) {
        if (loadedTypes.containsKey(configClass)) return loadedTypes.get(configClass);

        Map<Integer, ProxiedMethod> proxiedMethods = new IdentityHashMap<>();
        Map<String, Method> builderMethods = findBuilderMethods(configClass);

        Method[] methods = configClass.getMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(Value.class)) continue;

            Value value = method.getAnnotation(Value.class);

            //Building the loaders
            ProxiedValueLoader valueLoader;
            Method builderMethod = builderMethods.get(value.value());
            if (builderMethod != null) valueLoader = new BuilderProxiedValueLoader(method, builderMethod);
            else valueLoader = new SimpleProxiedValueLoader(method);

            ProxiedValueTransformer transformer;
            if (String.class.isAssignableFrom(method.getReturnType())) transformer = new PlaceholderProxiedValueTransformer(method);
            else transformer = new DefaultProxiedValueTransformer();

            ProxiedMethod proxiedMethod = new ProxiedMethod(method, valueLoader, transformer);
            proxiedMethods.put(proxiedMethod.getSlot(), proxiedMethod);
        }

        ProxiedConfigType<T> proxiedConfigType = new ProxiedConfigType<>(configClass, proxiedMethods);
        loadedTypes.put(configClass, proxiedConfigType);

        return proxiedConfigType;
    }

    /**
     * Returns the builder method for the given path
     *
     * @param clazz the clazz to search in
     * @return the method
     */
    private Map<String, Method> findBuilderMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .filter(Method::isDefault)
                .filter(m -> m.isAnnotationPresent(Builder.class))
                .filter(m -> m.getParameterCount() == 1 && String.class.isAssignableFrom(m.getParameterTypes()[0]))
                .collect(Collectors.toMap(m -> m.getAnnotation(Builder.class).value(), m -> m));
    }

}

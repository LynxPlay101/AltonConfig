package me.lynxplay.altonconfig.type.instance;

import me.lynxplay.altonconfig.AltonConfig;
import me.lynxplay.altonconfig.annotation.ConfigAutoCreate;
import me.lynxplay.altonconfig.annotation.method.DefaultValue;
import me.lynxplay.altonconfig.annotation.method.Value;
import me.lynxplay.altonconfig.exceptions.WrongDefaultValueException;
import me.lynxplay.altonconfig.methods.ProxiedMethod;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;

public class DefaultAltonConfig<T extends AltonConfig> {

    private final ProxiedAltonConfig<T> config;

    public DefaultAltonConfig(ProxiedAltonConfig<T> config) {
        this.config = config;
    }

    /**
     * Executes the given method in the alton config
     *
     * @param instance  the instance calling
     * @param method    the method that was called
     * @param arguments the arguments passed
     * @return the Object
     */
    public Object execute(Object instance, Method method, Object... arguments) throws Exception {
        String name = method.getName();
        if (name.equals("load") && arguments.length == 1 && arguments[0] instanceof File) {
            File file = (File) arguments[0];
            load(instance, file);
            return null;
        } else if (name.equals("getProxiedConfig")) {
            return config;
        }

        return null;
    }

    /**
     * Loads the instance from the given file
     *
     * @param instance the instance
     * @param file     the file
     * @throws Exception the exception that may be thrown
     */
    private void load(Object instance, File file) throws Exception {
        boolean storeProperties = false;

        Optional<ConfigAutoCreate> autoCreate = config.getTypeWrapper().isAutoCreate();
        if (autoCreate.isPresent()) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        }

        Properties properties = new Properties();
        properties.load(new FileReader(file));

        for (ProxiedMethod proxiedMethod : config.getTypeWrapper().getMethods()) { //Loop over every method and find properties value
            Value value = proxiedMethod.getConfigValue();
            Optional<String> defaultValue = proxiedMethod.getDefault().map(DefaultValue::value);

            try {
                if (!properties.containsKey(value.value())) throw new NoSuchElementException("Could not find the element for " + value.value());

                String property = properties.getProperty(value.value());
                config.setValue(proxiedMethod.getSlot(), proxiedMethod.load(instance, property == null ? null : (property.isEmpty() ? null : property))); //Try loading. If the property is "" or null pass on null
            } catch (Throwable t) {
                try {
                    config.setValue(proxiedMethod.getSlot(), proxiedMethod.load(instance, defaultValue.orElse(null))); //Fail loading and load default
                    properties.setProperty(value.value(), defaultValue.orElse("")); //Store default in properties instance

                    storeProperties = true;
                } catch (Throwable t2) {
                    throw new WrongDefaultValueException("The default value of " + value.value() + " (" + defaultValue + ") could not be parsed", t2);
                }
            }
        }

        if (!(storeProperties && autoCreate.map(ConfigAutoCreate::copyDefaults).orElse(false))) return; //No defaults to store ? Great we are done

        try (FileWriter writer = new FileWriter(file, false)) { //Store properties
            properties.store(writer, null);
        }
    }

}

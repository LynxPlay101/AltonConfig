package me.lynxplay.altonconfig;

import me.lynxplay.altonconfig.type.ConfigTypeBuilder;
import me.lynxplay.altonconfig.type.ProxiedConfigType;

import java.io.File;

public class AltonConfigCore {

    private ConfigTypeBuilder configTypeBuilder;

    public AltonConfigCore() {
        this.configTypeBuilder = new ConfigTypeBuilder();
    }

    /**
     * Returns the config type
     *
     * @param clazz the clazz
     * @param <T>   the generic type
     * @return the type
     */
    public <T extends AltonConfig> ProxiedConfigType<T> getType(Class<T> clazz) {
        return configTypeBuilder.load(clazz);
    }

    /**
     * Return the loaded config
     *
     * @param clazz the clazz to map to
     * @param file  the file to load from
     * @param <T>   the type
     * @return the instance
     */
    public <T extends AltonConfig> T load(Class<T> clazz, File file) {
        T t = getType(clazz).newInstance();
        t.load(file);
        return t;
    }
}

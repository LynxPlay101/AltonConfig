package me.lynxplay.altonconfig;

import me.lynxplay.altonconfig.type.instance.ProxiedAltonConfig;

import java.io.File;

public interface AltonConfig {

    /**
     * Saves the config to the disk
     */
    void load(File file);

    /**
     * Returns the proxied config
     *
     * @return the proxied config
     */
    ProxiedAltonConfig getProxiedConfig();

}

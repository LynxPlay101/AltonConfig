package me.lynxplay.altonconfig.type.instance;

import me.lynxplay.altonconfig.AltonConfig;
import me.lynxplay.altonconfig.type.ProxiedConfigType;

import java.util.IdentityHashMap;
import java.util.Map;

public class ProxiedAltonConfig<T extends AltonConfig> {

    private ProxiedConfigType<T> configType;
    private Map<Integer, Object> values = new IdentityHashMap<>();
    private DefaultAltonConfig<T> altonConfig;

    public ProxiedAltonConfig(ProxiedConfigType<T> configType) {
        this.configType = configType;
        this.altonConfig = new DefaultAltonConfig<>(this);
    }

    /**
     * Returns the config type wrapper this config is based on
     *
     * @return the type wrapper
     */
    public ProxiedConfigType<T> getTypeWrapper() {
        return configType;
    }

    /**
     * Returns the value at the slot of the method it is using
     *
     * @param slot the slot
     * @return the value
     */
    public Object getValue(int slot) {
        return values.get(slot);
    }

    /**
     * Sets the value on the given slot
     *
     * @param slot  the slot
     * @param value the value
     */
    public void setValue(int slot, Object value) {
        values.put(slot, value);
    }

    /**
     * Returns the super config type warpper
     *
     * @return the super config wrapper
     */
    public DefaultAltonConfig<T> getAltonSuperConfigTypeWrapper() {
        return altonConfig;
    }
}

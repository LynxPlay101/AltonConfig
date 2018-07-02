package me.lynxplay.altonconfig.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will make the alton-config loader auto create the file and load the given config if non is present
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfigAutoCreate {

    /**
     * Returns if the default values should be stored in the created file
     *
     * @return the default value
     */
    boolean copyDefaults() default true;

}

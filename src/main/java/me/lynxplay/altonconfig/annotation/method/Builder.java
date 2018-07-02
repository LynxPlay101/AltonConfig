package me.lynxplay.altonconfig.annotation.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adding this to a default method with one parameter {@link String} will make this method responsible for loading the field rather than the internal deserializer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Builder {

    /**
     * Returns the path of the object that will be mapped by this methode
     *
     * @return the value
     */
    String value();

}

package me.lynxplay.altonconfig.annotation.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be added to a string value to replace the given place holder in it
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Placeholder {

    /**
     * Returns the value the place holder will replace
     *
     * @return the value
     */
    String value();

}

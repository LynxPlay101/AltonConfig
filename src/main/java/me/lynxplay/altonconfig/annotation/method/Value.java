package me.lynxplay.altonconfig.annotation.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Value {

    /**
     * Returns the path of the value
     *
     * @return the path in the properties file
     */
    String value();
}

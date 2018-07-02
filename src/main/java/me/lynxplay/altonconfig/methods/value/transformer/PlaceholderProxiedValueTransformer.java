package me.lynxplay.altonconfig.methods.value.transformer;

import me.lynxplay.altonconfig.annotation.method.Placeholder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class PlaceholderProxiedValueTransformer implements ProxiedValueTransformer<String> {

    private Method method;
    private String[] placeholders;

    public PlaceholderProxiedValueTransformer(Method method) {
        this.method = method;
    }

    /**
     * Transforms the value based on the arguments passed
     *
     * @param value     the value to transform
     * @param arguments the arguments
     * @return the transformed value
     */
    @Override
    public String transform(String value, Object... arguments) {
        if (value == null) return null;

        lookupPlaceholders();

        for (int i = 0; i < placeholders.length; i++) {
            if (placeholders[i] == null) continue;

            value = value.replaceAll(placeholders[i], arguments[i].toString());
        }

        return value;
    }

    /**
     * Loads the placeholder annotation from the method parameters
     */
    private void lookupPlaceholders() {
        if (placeholders != null) return;
        this.placeholders = new String[method.getParameterCount()];

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int parameter = 0; parameter < parameterAnnotations.length; parameter++) {
            for (int annotationIndex = 0; annotationIndex < parameterAnnotations[parameter].length; annotationIndex++) {
                Annotation annotation = parameterAnnotations[parameter][annotationIndex];

                if (Placeholder.class.isInstance(annotation)) {
                    this.placeholders[parameter] = Placeholder.class.cast(annotation).value();
                }
            }
        }
    }
}

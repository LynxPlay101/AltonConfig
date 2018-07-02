package me.lynxplay.altonconfig.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {

    private static Field slotField;

    public static int getMethodSlot(Method method) {
        try {
            if (slotField == null) {
                slotField = Method.class.getDeclaredField("slot");
                slotField.setAccessible(true);
            }

            return (int) slotField.get(method);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

}

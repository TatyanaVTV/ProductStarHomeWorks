package ru.vtv.hw.practical.util;

import lombok.SneakyThrows;

public class ReflectionUtils {
    @SneakyThrows
    public static <T> T getPrivateField(Object target, String fieldName) {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(target);
    }
}

package ru.vtv.hw.practical.regexp;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {
    private final static String REMOVE_REGEX = "[a-zA-ZА-Яа-я:\s]";

    public static String clearString(String str) {
        if (str == null) return null;
        return str.replaceAll(REMOVE_REGEX, "");
    }
}

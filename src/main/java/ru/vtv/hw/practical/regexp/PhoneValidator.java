package ru.vtv.hw.practical.regexp;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@UtilityClass
public class PhoneValidator {
    private static final String PHONE_REGEX = "^\\+\\d{1,3}(?:[ -]*\\d{1,3})[ -]*(\\d+(?:[ -]*\\d+)*)$";

    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    public static boolean validate(String phone) {
        if (isNull(phone) || phone.isBlank()) return false;

        var split = phone.replaceAll("\\+", "").split("[ -]");
        if (split.length >= 2 && split[0].length() > 3) return false;
        if (split.length >= 2 && split[1].length() > 3) return false;

        var mather = PHONE_PATTERN.matcher(phone);
        if (!mather.matches()) return false;

        var mainNumber = mather.group(1).replaceAll("[ -]", "");

        var mainNumberLength = mainNumber.length();
        return mainNumberLength >= 5 && mainNumberLength <= 8;
    }
}

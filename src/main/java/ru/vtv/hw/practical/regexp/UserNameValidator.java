package ru.vtv.hw.practical.regexp;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@UtilityClass
public class UserNameValidator {
    private static final String USERNAME_REGEX = "^[A-Za-z][A-Z-a-z0-9_]{2,19}$";
    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);

    public static UserNameValidationResult validate(String username) {
        if (isNull(username)) return new UserNameValidationResult(false, null);

        var isValid = USERNAME_PATTERN.matcher(username).matches();

        return new UserNameValidationResult(isValid, isValid ? format(username) : null);
    }

    private static String format(String username) {
        return username.toLowerCase().replaceAll("_+", "_");
    }
}

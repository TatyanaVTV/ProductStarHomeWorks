package ru.vtv.hw.practical.coworking.exception;

import ru.vtv.hw.practical.coworking.User;

import static java.lang.String.format;

public class UserException extends RuntimeException {
    private UserException(String message) {
        super(message);
    }

    public static UserException alreadyRegistered(User user) {
        return new UserException(
                format("User '%s' (id = %s) is already registered in this CoworkingSystem!", user, user.getId())
        );
    }

    public static UserException unknownUser(User user) {
        return new UserException(
                format("User '%s' (id = %s) isn't registered in this CoworkingSystem!", user, user.getId())
        );
    }
}

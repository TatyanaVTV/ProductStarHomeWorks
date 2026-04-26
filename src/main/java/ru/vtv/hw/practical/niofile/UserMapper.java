package ru.vtv.hw.practical.niofile;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.niofile.UserManager.isValidCity;
import static ru.vtv.hw.practical.niofile.UserManager.isValidName;

@Slf4j
public class UserMapper {
    private static final String ROW_REGEXP = "[^,\\s]+\\s*,\\s*[^,\\s]+";
    public static final String NAME_CITY_REGEX = "^[а-яА-ЯёЁa-zA-Z][а-яА-ЯёЁa-zA-Z\\s-]*[а-яА-ЯёЁa-zA-Z]$";

    public static Optional<User> fromString(String line) {
        try {
            if (isNull(line) || line.trim().isEmpty()) {
                log.debug("Line is empty or null => skipped: {}", line);
                return Optional.empty();
            }

            if (!line.matches(ROW_REGEXP)) {
                log.warn("Line does not match 'name,city' format (non‑empty fields): {}", line);
                return Optional.empty();
            }

            var parts = line.split(",");
            var name = parts[0].trim();
            var city = parts[1].trim();

            if (!isValidName(name)) {
                log.warn("Invalid name format: {}", name);
                return Optional.empty();
            }
            if (!isValidCity(city)) {
                log.warn("Invalid city format: {}", city);
                return Optional.empty();
            }

            return Optional.of(new User(name, city));
        } catch (Exception e) {
            log.error("Error on parsing User from String: {}", line, e);
            return Optional.empty();
        }
    }
}

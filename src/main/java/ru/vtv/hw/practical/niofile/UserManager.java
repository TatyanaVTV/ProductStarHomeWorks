package ru.vtv.hw.practical.niofile;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static ru.vtv.hw.practical.niofile.UserMapper.NAME_CITY_REGEX;

@Slf4j
public class UserManager {
    private final List<User> users = new ArrayList<>();
    private final Path filePath;

    private final static String FILE_NAME = "users.txt";

    public UserManager() {
        this.filePath = Paths.get(FILE_NAME);
        loadFromFile();
    }


    public void addUser(User user) {
        users.add(user);
        saveToFile(user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public void loadFromFile() {
        try {
            if (Files.exists(filePath)) {
                var lines = Files.readAllLines(filePath);
                users.clear();

                lines.forEach(line -> {
                    var userOpt = UserMapper.fromString(line);
                    userOpt.ifPresent(users::add);
                });

                log.info("Load from file '{}', total users loaded: {}", filePath, users.size());
            } else {
                log.info("File not exists: {}. Empty users list has been initialized.", filePath);
            }
        } catch (IOException e) {
            log.error("Error on file reading: {}", filePath, e);
        }
    }

    public void saveToFile(User user) {
        try {
            var savedUserFormat = format("%s,%s%s", user.getName(), user.getCity(), System.lineSeparator());
            Files.write(filePath, savedUserFormat.getBytes(), CREATE, APPEND);
            log.info("User has been saved to file: {}", user);
        } catch (IOException e) {
            log.error("Error on saving user to file: {}", FILE_NAME, e);
        }
    }

    public static boolean isValidName(String name) {
        if (!name.matches(NAME_CITY_REGEX)) return false;
        long hyphenCount = name.chars().filter(ch -> ch == '-').count();
        return hyphenCount <= 1;
    }

    public static boolean isValidCity(String city) {
        return city.matches(NAME_CITY_REGEX);
    }
}

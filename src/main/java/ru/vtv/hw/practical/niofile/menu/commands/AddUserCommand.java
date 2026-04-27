package ru.vtv.hw.practical.niofile.menu.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.niofile.User;
import ru.vtv.hw.practical.niofile.UserManager;
import ru.vtv.hw.practical.niofile.menu.InputProvider;
import ru.vtv.hw.practical.niofile.menu.UserManagerCommand;

import static java.util.Objects.isNull;

@Slf4j
public class AddUserCommand implements UserManagerCommand {
    @Override
    public String getName() {
        return "Добавить пользователя";
    }

    @Override
    public boolean execute(UserManager userManager, InputProvider inputProvider) {
        var userName = requestStringInput(InputValueType.USER_NAME, inputProvider);
        var userCity = requestStringInput(InputValueType.USER_CITY, inputProvider);

        var user = new User(userName, userCity);
        userManager.addUser(user);

        // На английском, т.к. это сообьщение исключительно для логов сервиса
        log.debug("[MENU] New user has been added: {}", user);
        return true;
    }

    private String requestStringInput(InputValueType fieldType, InputProvider inputProvider) {
        String value = null;
        while (isNull(value) || value.isBlank()) {
            value = inputProvider.getInput("Введите " + fieldType.value);
            if (isNull(value) || value.isBlank()) {
                // на русском, т.к. это сообщение для вывода пользователю
                System.out.printf("Неверное значение: %s не может быть пустым!%n", fieldType.value);
            }

            if (!fieldType.isValidFormat(value)) {
                System.out.printf(
                        "Неверный формат: %s только буквы, пробелы и дефисы (не в начале/конце значения)!%n",
                        fieldType.value
                );
                value = null;
            }
        }
        return value;
    }

    @RequiredArgsConstructor
    private enum InputValueType {
        USER_NAME("имя пользователя"),
        USER_CITY("город пользователя");
        private final String value;

        private boolean isValidFormat(String value) {
            return switch (this) {
                case USER_NAME -> UserManager.isValidName(value);
                case USER_CITY -> UserManager.isValidCity(value);
            };
        }
    }
}

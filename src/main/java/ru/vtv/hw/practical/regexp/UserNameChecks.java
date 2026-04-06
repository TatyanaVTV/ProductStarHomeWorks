package ru.vtv.hw.practical.regexp;

import java.util.Scanner;

import static ru.vtv.hw.practical.regexp.UserNameValidator.validate;

public class UserNameChecks {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static String EXIT = "exit";

    public static void main(String[] args) {
        var givenLogin = "";
        UserNameValidationResult validationResult;

        while (true) {
            givenLogin = readInput();
            if (EXIT.equals(givenLogin)) return;

            validationResult = validate(givenLogin);

            if (!validationResult.valid()) {
                System.out.print("Некорректный логин. Попробуйте ещё раз. ");
                continue;
            }

            System.out.printf("Корректный логин: %s%n", validationResult.username());
        }
    }

    private static String readInput() {
        System.out.print("Введите логин: ");
        return SCANNER.nextLine();
    }
}

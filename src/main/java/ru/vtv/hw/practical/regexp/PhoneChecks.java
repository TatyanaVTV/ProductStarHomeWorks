package ru.vtv.hw.practical.regexp;

import java.util.Scanner;

import static ru.vtv.hw.practical.regexp.PhoneValidator.validate;

public class PhoneChecks {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static String EXIT = "exit";

    public static void main(String[] args) {
        var phone = "";
        var isValid = false;

        while (true) {
            phone = readInput();
            if (EXIT.equals(phone)) return;

            isValid = validate(phone);

            System.out.printf("Номер '%s' %sвалидный%n", phone, isValid ? "" : "не");
        }
    }

    private static String readInput() {
        System.out.print("Введите номер телефона для проверки: ");
        return SCANNER.nextLine();
    }
}

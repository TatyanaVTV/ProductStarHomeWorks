package ru.vtv.hw.practical.regexp;

import java.util.Scanner;

import static ru.vtv.hw.practical.regexp.StringUtil.clearString;

public class StringChecks {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static String EXIT = "exit";

    public static void main(String[] args) {
        var originalString = "";
        var fixedString = "";

        while (true) {
            originalString = readInput();
            if (EXIT.equals(originalString)) return;

            fixedString = clearString(originalString);

            System.out.printf("Скорректированная строка: %s%n", fixedString);
        }
    }

    private static String readInput() {
        System.out.print("Введите строку для удаления букв, пробелов и двоеточий: ");
        return SCANNER.nextLine();
    }
}

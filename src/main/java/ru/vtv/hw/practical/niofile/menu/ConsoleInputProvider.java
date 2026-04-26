package ru.vtv.hw.practical.niofile.menu;

import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {
    private final Scanner SCANNER = new Scanner(System.in);

    @Override
    public String getInput(String prompt) {
        System.out.print(prompt + ": ");
        return SCANNER.nextLine().trim();
    }
}

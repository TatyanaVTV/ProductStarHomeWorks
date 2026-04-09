package ru.vtv.hw.practical.adventurer;

import ru.vtv.hw.practical.adventurer.commands.InputProvider;

import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {
    private final Scanner SCANNER = new Scanner(System.in);

    @Override
    public String getInput(String prompt) {
        System.out.print(prompt + ": ");
        return SCANNER.nextLine().trim();
    }

    @Override
    public boolean getConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = SCANNER.nextLine().trim().toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
            System.out.println("Пожалуйста, введите 'y' или 'n'.");
        }
    }
}

package base.cycles;

import java.util.Scanner;

public class TableOfMultiply {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int digit = readDigit();
        printTableOfMultiply(digit);
    }

    private static int readDigit() {
        System.out.print("Введите число от 1 до 9: ");

        int digit;
        var validValue = false;
        do {
            digit = Integer.parseInt(SCANNER.nextLine());
            if (digit < 1 || digit > 9) {
                System.out.println("Число должно быть от 1 до 9");
            } else validValue = true;
        } while (!validValue);

        return digit;
    }

    private static void printTableOfMultiply(int digit) {
        int multiplier = 0;
        while (multiplier++ < 10) {
            System.out.printf("%d * %d = %d%n", digit, multiplier, digit * multiplier);
        }
    }
}

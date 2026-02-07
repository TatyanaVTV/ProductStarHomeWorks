package base.cycles;

import java.util.Random;
import java.util.Scanner;

public class SecretNumber {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RND = new Random();

    public static void main(String[] args) {
        var secretNumber = RND.nextInt(1, 101);
        System.out.println(secretNumber);

        var guessNumber = -1;
        do {
            if (guessNumber > 0) System.out.print("Неверно. Попробуйте ещё раз. ");
            guessNumber = readNumber();
        } while (guessNumber != secretNumber);

        System.out.printf("Вы угадали! Было загадано число %s%n", secretNumber);
    }

    private static int readNumber() {
        System.out.print("Введите число: ");

        int guessNumber;
        var validValue = false;
        do {
            guessNumber = Integer.parseInt(SCANNER.nextLine());
            if (guessNumber < 1 || guessNumber > 100) {
                System.out.println("Загадано число от 1 до 100");
            } else validValue = true;
        } while (!validValue);

        return guessNumber;
    }
}

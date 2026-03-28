package base.cycles;

import java.util.Scanner;

public class TimeOfDay {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int hour = readHour();
        var timeOfDay = defineTimeOfDay(hour);
        System.out.printf("Сейчас %s.%n", timeOfDay);
    }

    private static int readHour() {
        System.out.print("Введите час (0-23): ");

        int hour;
        var validValue = false;
        do {
            hour = Integer.parseInt(SCANNER.nextLine());
            if (hour < 0 || hour > 23) {
                System.out.println("Час должен быть от 0 до 23");
            } else validValue = true;
        } while (!validValue);

        return hour;
    }

    private static String defineTimeOfDay(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("[ERROR] Wrong value for hour. Valid values: from 0 to 23");
        }

        return switch (hour) {
            case 6, 7, 8, 9, 10, 11 -> "утро";
            case 12, 13, 14, 15, 16, 17 -> "день";
            case 18, 19, 20, 21 -> "вечер";
            default -> "ночь";
        };
    }
}

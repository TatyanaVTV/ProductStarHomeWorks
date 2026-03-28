package base.types;

import java.util.Scanner;

public class MinutesConverter {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        var totalMinutes = readMinutes();
        convertToHoursAndMinutes(totalMinutes);
    }

    private static int readMinutes() {
        System.out.print("Введите количество минут: ");

        var minutes = 0;
        while ((minutes = Integer.parseInt(SCANNER.nextLine())) < 0) {
            System.out.print("Число минут должно быть больше 0. Попробуйте ещё раз: ");
        }
        return minutes;
    }

    private static void convertToHoursAndMinutes(int totalMinutes) {
        var hours = totalMinutes / 60;
        var minutes = totalMinutes % 60;

        System.out.printf("Это равно: %s час%s %s минут%s",
                hours, defineHoursEnding(hours), minutes, defineMinutesEnding(minutes));
    }

    private static String defineHoursEnding(int hours) {
        var hoursStep = hours % 10;

        var hoursEnding = "ов";
        if (hoursStep == 1) {
            hoursEnding = "";
        } else if (hoursStep >= 2 && hoursStep <= 4) {
            hoursEnding = "а";
        }

        return hoursEnding;
    }

    private static String defineMinutesEnding(int minutes) {
        var minutesStep = minutes % 10;

        var minutesEnding = "";
        if (minutesStep == 1) {
            minutesEnding = "а";
        } else if (minutesStep >= 2 && minutesStep <= 4) {
            minutesEnding = "ы";
        }
        return minutesEnding;
    }
}

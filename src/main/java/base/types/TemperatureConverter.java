package base.types;

import java.util.Scanner;

public class TemperatureConverter {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        var celsius = readCelsius();
        var fahrenheit = convertToFahrenheit(celsius);
        System.out.printf("Температура в градусах Фаренгейта: %s", fahrenheit);
    }

    private static double readCelsius() {
        System.out.print("Введите температуру в градусах Цельсия: ");
        return Double.parseDouble(SCANNER.nextLine());
    }

    private static double convertToFahrenheit(double celsius) {
        return (celsius * 9.0) / 5 + 32;
    }
}

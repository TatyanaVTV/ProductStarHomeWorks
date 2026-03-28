package base.types;

import java.util.Scanner;

public class RectangleArea {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        var length = readValue("длину");
        var width = readValue("ширину");
        var area = calculateArea(length, width);
        System.out.printf("Площадь прямоугольника: %s", area);
    }

    private static double readValue(String name) {
        System.out.printf("Введите %s прямоугольника: ", name);
        return Double.parseDouble(SCANNER.nextLine());
    }

    private static double calculateArea(double length, double width) {
        return length * width;
    }
}

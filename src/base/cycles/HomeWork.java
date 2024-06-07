package base.cycles;

import java.util.Scanner;
import java.util.StringJoiner;

public class HomeWork {
    public static void main(String[] args) {
        System.out.println("Введите число: ");
        Scanner in = new Scanner(System.in);

        int number = Integer.parseInt(in.nextLine());
        StringJoiner answer = new StringJoiner(" ");

        /* Без условия i > 0 => 0 также заменяется, т.к. 0 % 3 и 0 % 5 также = 0,
                        но в примере из задания - ноль не заменялся, поэтому добавлено это условие */
        for (int i = 0; i <= number; i++) {
            String valueToAdd = getStrForValue(i); // альтернативный вариант

            /*
            if (i > 0 && i % 3 == 0) {
                valueToAdd += "fizz";
            }
            if (i > 0 && i % 5 == 0) {
                valueToAdd += "buzz";
            }
            valueToAdd = (valueToAdd.isEmpty()) ? String.valueOf(i) : valueToAdd;
            */
            answer.add(valueToAdd);
        }

        System.out.println(answer);
    }

    private static String getStrForValue(int value) {
        String valueToAdd = "";

        if (value > 0 && value % 3 == 0 && value % 5 == 0) {
            valueToAdd += "fizzbuzz";
        } else if (value > 0 && value % 3 == 0) {
            valueToAdd += "fizz";
        } else if (value > 0 && value % 5 == 0) {
            valueToAdd += "buzz";
        } else {
            valueToAdd += String.valueOf(value);
        }
        return valueToAdd;
    }
}

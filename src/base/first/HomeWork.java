package base.first;

import java.util.Scanner;

public class HomeWork {
    public static void main(String[] args) {
        System.out.println("Расскажите о Вашем друге");

        Scanner in = new Scanner(System.in,"Cp866");

        System.out.println("Как зовут Вашего друга?");
        String friendName = in.nextLine();

        System.out.println("Сколько лет Вашему другу?");
        String friendAge = in.nextLine();

        System.out.println("Моему другу " + friendName + " сейчас " + friendAge + " лет.");
        in.close();
    }
}

package base.firstprogramm;

import java.util.Scanner;

import static base.firstprogramm.Person.findPerson;
import static base.firstprogramm.Person.initBD;

public class PhoneBook {
    public static final String EXIT_COMMAND = "выход";

    public static void main(String[] args) {
        initBD();

        try (var in = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.print("\nДля поиcка по имени введите 1, по номеру 2, email 3: ");
                    String searchType = readValue(in, true);

                    System.out.print("Введите поисковое значение: ");
                    String searchString = readValue(in, false);

                    findPerson(searchType, searchString);
                } catch (ExitInitializedException ex) {
                    System.out.println(ex.getLocalizedMessage());
                    return;
                } catch (WrongSearchTypeException | EmptyValueTypedException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        }
    }

    private static String readValue(Scanner reader, boolean forSearchType) throws ExitInitializedException, WrongSearchTypeException, EmptyValueTypedException {
        String typedCommand = reader.nextLine();
        if (typedCommand.equals(EXIT_COMMAND)) {
            throw new ExitInitializedException();
        }

        if (!typedCommand.matches("[A-Za-zА-ЯЁа-яё0-9@\\.\\+]+")) {
            throw new EmptyValueTypedException();
        }

        if (forSearchType && !typedCommand.matches("[1-3]?")) {
            throw new WrongSearchTypeException();
        }

        return typedCommand;
    }
}

class Person {
    private String name;
    private String phone;
    private String email;
    private static Person[] persons = new Person[10];

    public Person(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    String getName() {
        return name;
    }

    String getPhone() {
        return phone;
    }

    String getEmail() {
        return email;
    }

    public static void initBD() {
        persons[0] = new Person("Юля", "89210000000", "Julia@yandex.com");
        persons[1] = new Person("Сергей", "777777", "borya@yandex.com");
        persons[2] = new Person("Друган", "23566777", "univer@yandex.com");
        persons[3] = new Person("EvilBoss", "456546546", "boss@yandex.com");
        persons[4] = new Person("Anna", "+79216661666", "mylove@yandex.com");
    }

    public String toString() {
        return "\nName: " + this.getName() + "\nPhone number: " + this.getPhone() + "\nEmail: " +
                this.getEmail();
    }

    // поиск человека
    public static void findPerson(String searchType, String searchString) {
        switch (searchType) {
            case "1":
                //по имени
                findBySearchTypeAndValue(SearchType.NAME, searchString, persons);
                break;
            case "2":
                //по телефону
                findBySearchTypeAndValue(SearchType.PHONE, searchString, persons);
                break;
            case "3":
                // по почте
                findBySearchTypeAndValue(SearchType.EMAIL, searchString, persons);
                break;
            default:
                break;
        }
    }

    // Одна ф-ция через enum для устранения избыточности кода (3 практически одинаковые ф-ции по имени / телефону / email)
    public static Person findBySearchTypeAndValue(SearchType searchType, String valueToSearch, Person[] persons) {
        for (Person person : persons) {
            if (person == null) continue;

            String checkedValue = switch (searchType) {
                case NAME -> person.getName();
                case PHONE -> person.getPhone();
                case EMAIL -> person.getEmail();
            };

            if (checkedValue.equals(valueToSearch)) {
                System.out.println(person);
                return person;
            }
        }
        System.out.println("Человек с " + searchType.forStr + " '" + valueToSearch + "' не найден!");
        return null;
    }

}

enum SearchType {
    NAME("именем"), PHONE("телефоном"), EMAIL("адресом email");

    public final String forStr;

    SearchType(String forStr) {
        this.forStr = forStr;
    }
}

/*
 ДЗ
Дописать методы поиска для телефона и почты
Сделать так же выход
Продумать обработку исключений для поиска по пустым значениям

*/
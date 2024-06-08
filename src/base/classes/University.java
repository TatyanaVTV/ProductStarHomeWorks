package base.classes;

import java.util.Arrays;

public class University {
    public static void main(String[] args) {
        Person[] participants = {
                new Docent("Vasilij", "Popov"),
                new Docent("Alexandra", "Samoilova"),
                new Student("Alexey", "Ivanov"),
                new Student("Maria", "Filatova"),
                new Student("Matvej", "Mohovoj"),
                new Student("Nikolaj", "Samohvalov"),
                new Student("Svetlana", "Semenova"),
                new Student("Dmitrij", "Novokov")
        };

        Arrays.stream(participants).forEach(System.out::println);

        Arrays.stream(participants).forEach(person -> {
            person.attendLecture();
            person.signFireProtectionEducation();
        });
    }
}

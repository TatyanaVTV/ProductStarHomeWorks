package base.classes;

public class Docent extends Person {
    public Docent(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void attendLecture() {
        System.out.println("Docent " + firstName + " " + lastName + " gave a lecture to the students!");
    }
}

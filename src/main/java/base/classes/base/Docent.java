package base.classes.base;

public class Docent extends Person {
    public Docent(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void attendLecture() {
        System.out.println("Docent " + getFirstName() + " " + getLastName() + " gave a lecture to the students!");
    }
}

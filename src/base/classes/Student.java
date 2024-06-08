package base.classes;

public class Student extends Person {
    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void attendLecture() {
        System.out.println("Student " + firstName + " " + lastName + " attended a lecture!");
    }
}

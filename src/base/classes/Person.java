package base.classes;

public abstract class Person {
    String firstName;
    String lastName;
    boolean isSignedFireProtectionEducation;

    public abstract void attendLecture();

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void signFireProtectionEducation() {
        this.isSignedFireProtectionEducation = true;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + (isSignedFireProtectionEducation ? "" : " [ATTENTION: FireProtectionEducation is not signed!]");
    }
}

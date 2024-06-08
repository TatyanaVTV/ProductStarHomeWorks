package base.classes.base;

public abstract class Person {
    private final String firstName;
    private final String lastName;
    boolean isSignedFireProtectionEducation;

    public abstract void attendLecture();

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void signFireProtectionEducation() {
        this.isSignedFireProtectionEducation = true;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + (isSignedFireProtectionEducation ? "" : " [ATTENTION: FireProtectionEducation is not signed!]");
    }
}

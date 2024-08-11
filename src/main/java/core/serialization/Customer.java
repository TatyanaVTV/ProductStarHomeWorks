package core.serialization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(value = { "password" }) // Один из вариантов заставить Jackson пропускать это поле при сериализации
public class Customer implements Serializable {
    private String surname;
    private String firstname;
    @JsonIgnore private transient String password; // Один из вариантов заставить Jackson пропускать это поле при сериализации
    private Passport passport;

    public Customer() {
        this("n/a", "n/a", "n/a", null);
    }

    public Customer(String surname, String firstname, String password, Passport passport) {
        this.surname = surname;
        this.firstname = firstname;
        this.password = password;
        this.passport = passport;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonIgnore // Один из вариантов заставить Jackson пропускать это поле при сериализации
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    @Override
    public String toString() {
        return String.format("Customer: \n\t%s %s\n\t%s\n\t%s", surname, firstname, password, passport);
    }
}

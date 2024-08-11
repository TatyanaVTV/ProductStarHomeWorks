package core.serialization;

public class Passport {
    private String serial;
    private String number;

    public Passport() {
        this("n/a", "n/a");
    }

    public Passport(String serial, String number) {
        this.serial = serial == null ? "n/a" : serial;
        this.number = number == null ? "n/a" : number;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("Passport: %s %s", serial, number);
    }
}

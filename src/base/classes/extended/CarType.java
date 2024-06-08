package base.classes.extended;

public enum CarType {
    PassengerCar("Легковой"), PickUp("Пикап"), Suv("Внедорожник");

    private final String rusName;

    CarType(String rusName) {
        this.rusName = rusName;
    }

    @Override
    public String toString() {
        return rusName;
    }
}

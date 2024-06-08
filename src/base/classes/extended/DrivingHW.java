package base.classes.extended;

public class DrivingHW {
    public static void main(String[] args) {
        driveCar(new GasolineCar("LADA Niva Legend 3-дв.", 4500, CarType.Suv, 83));
        driveCar(new ElectroCar("Tesla Model S", 3500, CarType.PassengerCar, 500));
        driveCar(new ElectroCar("Mitsubishi L200 V", 5500, CarType.PickUp, 136));
    }

    public static void driveCar(Drivable car) {
        System.out.println(car);
        car.drive();
    }
}

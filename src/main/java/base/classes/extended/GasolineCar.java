package base.classes.extended;

public class GasolineCar extends Car {
    public GasolineCar(String name, int weight, CarType carType, int power) {
        super(name, weight, carType, EngineType.Gasoline, power);
    }

    @Override
    public void drive() {
        getEngine().startEngine();
        getWheel().turnRight();
        getWheel().turnLeft();
        getEngine().stopEngine();
    }
}

package base.classes.extended;

public class ElectroCar extends Car {
    public ElectroCar(String name, int weight, CarType carType, int power) {
        super(name, weight, carType, EngineType.Electro, power);
    }

    @Override
    public void drive() {
        getWheel().turnRight();
        getWheel().turnLeft();
    }
}

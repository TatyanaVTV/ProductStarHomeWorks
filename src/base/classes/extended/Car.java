package base.classes.extended;

public abstract class Car implements Drivable {
    private final String name;
    private final int weight;
    private final CarType carType;
    private final Wheel wheel;
    private final Engine engine;

    public Car(String name, int weight, CarType carType, EngineType engineType, int power) {
        this.name = name;
        this.weight = weight;
        this.carType = carType;
        this.wheel = new Wheel();
        this.engine = new Engine(power, engineType);
    }

    public Wheel getWheel() {
        return wheel;
    }

    public Engine getEngine() {
        return engine;
    }

    @Override
    public String toString() {
        return String.format("\n%s, %d kg, %s, %s", name, weight, carType, engine);
    }
}

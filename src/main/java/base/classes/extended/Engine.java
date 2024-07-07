package base.classes.extended;

public class Engine {
    private final int power;
    private final EngineType engineType;

    public Engine(int power, EngineType engineType) {
        this.power = power;
        this.engineType = engineType;
    }

    public void startEngine() {
        System.out.println("Engine is started!");
    }
    public void stopEngine() {
        System.out.println("Engine is stopped!");
    }

    @Override
    public String toString() {
        return String.format("Engine{ %s hp, %s }", power, engineType);
    }
}

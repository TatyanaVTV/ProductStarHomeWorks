package base.classes.extended;

public enum EngineType {
    Gasoline, Electro;

    @Override
    public String toString() {
        return name();
    }
}

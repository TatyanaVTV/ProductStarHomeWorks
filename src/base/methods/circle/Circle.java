package base.methods.circle;

/**
 * Заполните этот класс в соответсвии с заданием из лекции.
 */
public class Circle {
    private double radius;

    public Circle(double radius) {
        setRadius(radius);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius cannot be negative or equal to zero!");
        }
        this.radius = radius;
    }

    public double getArea() {
        return Math.PI * Math.pow(getRadius(), 2);
    }

    @Override
    public String toString() {
        return String.format("Circle with radius %f", getRadius());
    }
}
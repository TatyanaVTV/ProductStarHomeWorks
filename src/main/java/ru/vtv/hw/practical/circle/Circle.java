package ru.vtv.hw.practical.circle;

import lombok.Data;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

@Data
public class Circle {
    private double radius;

    public void setRadius(double radius) {
        if (radius < 0) throw new IllegalArgumentException("Radius cannot be negative!");

        this.radius = radius;
    }

    public double getArea() {
        return PI * pow(radius, 2);
    }
}

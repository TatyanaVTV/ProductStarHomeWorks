package ru.vtv.hw.practical.circle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Circle {
    private double radius;

    public void setRadius(double radius) {
        if (radius < 0) throw new IllegalArgumentException("Радиус должен быть больше нуля!");

        this.radius = radius;
    }

    public double getArea() {
        return PI * pow(radius, 2);
    }
}

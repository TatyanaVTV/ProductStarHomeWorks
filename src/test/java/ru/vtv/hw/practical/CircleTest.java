package ru.vtv.hw.practical;

import org.junit.jupiter.api.Test;
import ru.vtv.hw.practical.circle.Circle;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CircleTest {

    @Test
    void testAllArgsConstructor_validStoredValue() {
        var circle = new Circle(7.5);

        assertEquals(7.5, circle.getRadius(), 0.001,
                "Геттер должен возвращать сохранённое значение радиуса");
    }

    @Test
    void testNoArgsConstructor_validStoredValue() {
        var circle = new Circle(0.0);

        assertEquals(0.0, circle.getRadius(), 0.001,
                "Геттер должен возвращать сохранённое значение радиуса");
    }

    @Test
    void testGetRadius_returnsStoredValue() {
        var circle = new Circle();
        circle.setRadius(7.5);

        assertEquals(7.5, circle.getRadius(), 0.001,
                "Геттер должен возвращать сохранённое значение радиуса");
    }

    @Test
    void testGetArea_withPositiveRadius() {
        var circle = new Circle();
        circle.setRadius(5.0);

        var expectedArea = PI * pow(5.0, 2);
        var actualArea = circle.getArea();

        assertEquals(expectedArea, actualArea, 0.001,
                "Площадь круга должна вычисляться по формуле π×r² для положительного радиуса!");
    }

    @Test
    void testGetArea_ZeroRadius() {
        var circle = new Circle();
        circle.setRadius(0.0);

        assertEquals(0.0, circle.getRadius(), 0.001,
                "Радиус должен быть 0 после установки!");
        assertEquals(0.0, circle.getArea(), 0.001,
                "При радиусе 0 площадь круга должна быть равна 0!");
    }

    @Test
    void testGetArea_withLargeRadius() {
        var circle = new Circle();
        circle.setRadius(100.0);

        var expectedArea = PI * pow(100.0, 2);
        var actualArea = circle.getArea();

        assertEquals(expectedArea, actualArea, 0.001,
                "Некорректно вычислена площадь круга для большого радиуса!");
    }

    @Test
    void testGetArea_precision() {
        var circle = new Circle();
        circle.setRadius(2.5);

        var expectedArea = PI * 6.25; // 2.5² = 6.25
        var actualArea = circle.getArea();

        assertEquals(expectedArea, actualArea, 0.0001,
                "Некорректно вычислена площадь круга для дробного радиуса!");
    }

    @Test
    void testSetRadius_withNegativeValue_throwsException() {
        var circle = new Circle();

        var exception = assertThrows(IllegalArgumentException.class,
                () -> circle.setRadius(-5.0),
                "Установка отрицательного радиуса должна вызывать ошибку!");
        assertEquals("Радиус должен быть больше нуля!", exception.getMessage());
    }

    @Test
    void testRadiusValidation_edgeCase() {
        var circle = new Circle();

        // Граничный случай — ноль допустим
        circle.setRadius(0.0);
        assertEquals(0.0, circle.getRadius(), 0.001);

        // Отрицательное значение должно вызвать исключение
        assertThrows(IllegalArgumentException.class,
                () -> circle.setRadius(-0.0001),
                "Даже небольшое отрицательное значение должно вызывать исключение!");
    }
}

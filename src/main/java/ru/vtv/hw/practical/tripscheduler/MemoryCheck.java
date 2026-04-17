package ru.vtv.hw.practical.tripscheduler;

import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.tripscheduler.trips.Trip;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static ru.vtv.hw.practical.tripscheduler.TripFactory.createRandomTrip;

@Slf4j
public class MemoryCheck {
    private static final List<Trip> leakyTripList = new ArrayList<>();
    private static final int LEAKY_TRIP_COUNT = 10;

    private static final List<WeakReference<Trip>> weakReferenceTripList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== СРАВНЕНИЕ УТЕЧКИ ПАМЯТИ vs WEAKREFERENCE ===");

        printMemoryUsage("Начальное состояние");

        leakyTripCheck();
        weakReferenceCheck();
    }

    private static void leakyTripCheck() {
        System.out.println("\n--- СЦЕНАРИЙ 1: УТЕЧКА ПАМЯТИ (List<Trip>) ---");

        printMemoryUsage("Перед созданием " + LEAKY_TRIP_COUNT + " объектов в leakyTripList");

        var count = 0;
        while (count++ < LEAKY_TRIP_COUNT) {
            leakyTripList.add(createRandomTrip());
        }

        printMemoryUsage("После создания " + LEAKY_TRIP_COUNT + " объектов в leakyTripList");


        System.out.println("Очистка leakyTripList...");
        leakyTripList.clear();

        callGCAndWait("После очистки leakyTripList и вызова System.gc()");
    }

    private static void weakReferenceCheck() {
        System.out.println("\n--- СЦЕНАРИЙ 2: WEAKREFERENCE (List<WeakReference<Trip>>) ---");

        printMemoryUsage("Перед созданием " + LEAKY_TRIP_COUNT + " WeakReference в weakReferenceTripList");

        var count = 0;
        while (count++ < LEAKY_TRIP_COUNT) {
            weakReferenceTripList.add(new WeakReference<>(createRandomTrip()));
        }

        printMemoryUsage("После создания " + LEAKY_TRIP_COUNT + " WeakReference в weakReferenceTripList");

        System.out.println("Очистка weakReferenceTripList...");
        weakReferenceTripList.clear();

        callGCAndWait("После очистки weakReferenceTripList и вызова System.gc()");
    }

    private static void printMemoryUsage(String stage) {
        var runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("%n--- %s ---%n", stage);
        System.out.printf("Использовано памяти: %,d КБ%n", usedMemory / 1024);
    }

    private static void callGCAndWait(String stage) {
        System.out.println("Вызов сборщика мусора...");
        System.gc();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            log.error("Calling GC error: {}", e.getMessage());
        }

        printMemoryUsage(stage);
    }
}

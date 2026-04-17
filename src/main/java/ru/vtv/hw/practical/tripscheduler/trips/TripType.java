package ru.vtv.hw.practical.tripscheduler.trips;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public enum TripType {
    NOT_DEFINED,
    BUSINESS,
    TOURIST,
    EDUCATION;

    public static TripType randomTripType() {
        var validTypes = Arrays.stream(TripType.values())
                .filter(type -> type != NOT_DEFINED)
                .toList();
        int randomIndex = ThreadLocalRandom.current().nextInt(validTypes.size());
        return validTypes.get(randomIndex);
    }
}

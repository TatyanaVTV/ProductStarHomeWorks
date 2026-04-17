package ru.vtv.hw.practical.tripscheduler.trips;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class TouristTrip extends Trip {
    @Override
    public void planTrip() {
        System.out.printf("Планирование туристической поездки (%s)%n", getId());
    }
}

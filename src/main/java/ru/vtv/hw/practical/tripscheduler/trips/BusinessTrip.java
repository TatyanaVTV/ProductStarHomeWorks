package ru.vtv.hw.practical.tripscheduler.trips;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BusinessTrip extends Trip {
    @Override
    public void planTrip() {
        System.out.printf("Планирование бизнес-поездки (%s)%n", getId());
    }
}

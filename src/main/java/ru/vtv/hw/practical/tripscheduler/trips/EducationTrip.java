package ru.vtv.hw.practical.tripscheduler.trips;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class EducationTrip extends Trip {
    @Override
    public void planTrip() {
        System.out.printf("Планирование учебной поездки (%s)%n", getId());
    }
}

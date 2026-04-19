package ru.vtv.hw.practical.tripscheduler.observers;

import ru.vtv.hw.practical.tripscheduler.trips.Trip;

public class LoggingTripObserver implements TripObserver {
    @Override
    public void onTripCreated(Trip trip) {
        System.out.printf("[LOG] New trip created: %s (type: %s)%n", trip.getId(), trip.getTripType());
    }

    @Override
    public void onTripPlanned(Trip trip) {
        System.out.printf("[LOG] Trip planned: %s%n", trip.getId());
    }

    @Override
    public void onTripCancelled(Trip trip) {
        System.out.printf("[LOG] Trip cancelled: %s%n", trip.getId());
    }
}

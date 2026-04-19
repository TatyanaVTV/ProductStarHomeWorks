package ru.vtv.hw.practical.tripscheduler.observers;

import ru.vtv.hw.practical.tripscheduler.trips.Trip;

public interface TripObserver {
    void onTripCreated(Trip trip);
    void onTripPlanned(Trip trip);
    void onTripCancelled(Trip trip);
}

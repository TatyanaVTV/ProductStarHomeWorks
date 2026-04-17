package ru.vtv.hw.practical.tripscheduler.observers;

import ru.vtv.hw.practical.tripscheduler.trips.Trip;

public interface TripEventable {
    void addObserver(TripObserver observer);
    void removeObserver(TripObserver observer);
    void notifyObservers(TripEventType eventType, Trip trip);
}

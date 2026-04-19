package ru.vtv.hw.practical.tripscheduler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.tripscheduler.observers.TripEventType;
import ru.vtv.hw.practical.tripscheduler.observers.TripEventable;
import ru.vtv.hw.practical.tripscheduler.observers.TripObserver;
import ru.vtv.hw.practical.tripscheduler.trips.Trip;
import ru.vtv.hw.practical.tripscheduler.trips.TripType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;
import static ru.vtv.hw.practical.tripscheduler.observers.TripEventType.*;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class TripManager implements TripEventable {
    private static final TripManager INSTANCE = new TripManager();
    private final Map<UUID, Trip> trips = new ConcurrentHashMap<>();

    private final List<TripObserver> observers = new ArrayList<>();

    public static TripManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void addObserver(TripObserver observer) {
        if (!observers.contains(observer) && !isNull(observer)) {
            observers.add(observer);
            log.info("Added observer {}", observer);
        }
    }

    @Override
    public void removeObserver(TripObserver observer) {
        if (observers.contains(observer) && !isNull(observer)) {
            observers.remove(observer);
            log.info("Removed observer {}", observer);
        }
    }

    @Override
    public void notifyObservers(TripEventType eventType, Trip trip) {
        switch (eventType) {
            case CREATED: observers.forEach(observer -> observer.onTripCreated(trip));
            case PLANNED: observers.forEach(observer -> observer.onTripPlanned(trip));
            case CANCELED: observers.forEach(observer -> observer.onTripCancelled(trip));
        }
    }

    public Trip createTrip(TripType type, LocalDateTime date, List<Tourist> tourists, List<Waypoint> waypoints) {
        var trip = TripFactory.createTrip(type, date, tourists, waypoints);
        trips.put(trip.getId(), trip);
        log.info("Stored trip: {}", trip.getId());
        notifyObservers(CREATED, trip);
        return trip;
    }

    public void planTrip(UUID tripId) {
        var trip = trips.get(tripId);
        if (!isNull(trip)) {
            trip.planTrip();
            notifyObservers(PLANNED, trip);
            log.info("Planned trip: {}", tripId);
        } else {
            log.warn("Trip not found: {}", tripId);
        }
    }

    public void cancelTrip(UUID tripId) {
        var trip = trips.get(tripId);
        if (!isNull(trip)) {
            trips.remove(tripId);
            log.info("Canceled trip: {}", tripId);
            notifyObservers(CANCELED, trip);
        } else {
            log.warn("Trip not found: {}", tripId);
        }
    }

    public List<Trip> getAllTrips() {
        return new ArrayList<>(trips.values());
    }

    public Trip getTrip(UUID tripId) {
        return trips.get(tripId);
    }
}

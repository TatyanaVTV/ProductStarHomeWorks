package ru.vtv.hw.practical.tripscheduler;

import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.tripscheduler.trips.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.vtv.hw.practical.tripscheduler.trips.TripType.*;

@Slf4j
public abstract class TripFactory {
    private static final Random RND = new Random();
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public static Trip createRandomTrip() {
        var randomTripDate = LocalDateTime.now().plusDays(RND.nextInt(15));
        var tourists = new ArrayList<Tourist>();
        var waypoints = new ArrayList<Waypoint>();

        var touristsCount = RND.nextInt(5, 25);
        var waypointsCount = RND.nextInt(5, 10);

        var count = 0;
        while (count < touristsCount) {
            tourists.add(createRandomTourist(++count));
        }

        count = 0;
        while (count < waypointsCount) {
            waypoints.add(createRandomWaypoint(++count));
        }

        var type = randomTripType();
        return createTrip(type, randomTripDate, tourists, waypoints);
    }

    public static Trip createTrip(TripType type, LocalDateTime date, List<Tourist> tourists, List<Waypoint> waypoints) {
        log.debug("Creating trip #{}...", COUNTER.incrementAndGet());
        return switch (type) {
            case NOT_DEFINED, TOURIST -> createTouristTrip(date, tourists, waypoints);
            case BUSINESS -> createBusinessTrip(date, tourists, waypoints);
            case EDUCATION -> createEducationTrip(date, tourists, waypoints);
        };
    }

    public static Trip createBusinessTrip(LocalDateTime tripDate, List<Tourist> tourists, List<Waypoint> waypoints) {
        return BusinessTrip.builder()
                .tripDate(tripDate)
                .nights(waypoints.size())
                .touristsList(tourists)
                .waypointsList(waypoints)
                .tripType(BUSINESS)
                .build();
    }

    public static Trip createEducationTrip(LocalDateTime tripDate, List<Tourist> tourists, List<Waypoint> waypoints) {
        return EducationTrip.builder()
                .tripDate(tripDate)
                .nights(waypoints.size())
                .touristsList(tourists)
                .waypointsList(waypoints)
                .tripType(EDUCATION)
                .build();
    }

    public static Trip createTouristTrip(LocalDateTime tripDate, List<Tourist> tourists, List<Waypoint> waypoints) {
        return TouristTrip.builder()
                .tripDate(tripDate)
                .nights(waypoints.size())
                .touristsList(tourists)
                .waypointsList(waypoints)
                .tripType(TOURIST)
                .build();
    }

    private static Tourist createRandomTourist(int order) {
        return Tourist.builder()
                .id(UUID.randomUUID())
                .lastName("Lastname_" + order)
                .firstName("Firstname_" + order)
                .build();
    }

    private static Waypoint createRandomWaypoint(int order) {
        return Waypoint.builder()
                .id(UUID.randomUUID())
                .name("Waypoint #" + order)
                .price(BigDecimal.valueOf(RND.nextDouble(1000, 2500)))
                .build();
    }
}

package ru.vtv.hw.practical.tripscheduler;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class TripFactory {
    private static final Random RND = new Random();
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public static Trip createTrip() {
        var randomTripDate = LocalDateTime.now().plusDays(RND.nextInt(15));
        var tourists = new ArrayList<Tourist>();
        var waypoints = new ArrayList<Waypoint>();

        var touristsCount = RND.nextInt(5, 25);
        var waypointsCount = RND.nextInt(5, 10);

        var count = 0;
        while (count < touristsCount) {
            tourists.add(createTourist(++count));
        }

        count = 0;
        while (count < waypointsCount) {
            waypoints.add(createWaypoint(++count));
        }

        log.debug("Creating trip #{}...", COUNTER.incrementAndGet());
        return Trip.builder()
                .tripDate(randomTripDate)
                .touristsList(tourists)
                .waypointsList(waypoints)
                .build();
    }

    private static Tourist createTourist(int order) {
        return Tourist.builder()
                .id(UUID.randomUUID())
                .lastName("Lastname_" + order)
                .firstName("Firstname_" + order)
                .build();
    }

    private static Waypoint createWaypoint(int order) {
        return Waypoint.builder()
                .id(UUID.randomUUID())
                .name("Waypoint #" + order)
                .price(BigDecimal.valueOf(RND.nextDouble(1000, 2500)))
                .build();
    }
}

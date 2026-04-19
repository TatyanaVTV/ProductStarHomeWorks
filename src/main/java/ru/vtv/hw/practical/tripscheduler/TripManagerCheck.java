package ru.vtv.hw.practical.tripscheduler;

import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.tripscheduler.observers.EmailNotificationTripObserver;
import ru.vtv.hw.practical.tripscheduler.observers.LoggingTripObserver;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static ru.vtv.hw.practical.tripscheduler.trips.TripType.*;

@Slf4j
public class TripManagerCheck {
    private static final TripManager MANAGER = TripManager.getInstance();
    private static final Random RND = new Random();

    private static final LoggingTripObserver LOGGING_TRIP_OBSERVER = new LoggingTripObserver();
    private static final EmailNotificationTripObserver EMAIL_TRIP_OBSERVER = new EmailNotificationTripObserver();

    private static final Tourist BUSINESSMAN = Tourist.builder()
            .lastName("Бизнесменовый")
            .firstName("Бизнесмен")
            .build();
    private static final Tourist STUDENT = Tourist.builder()
            .lastName("Университетский")
            .firstName("Студент")
            .build();
    private static final Tourist RANDOM_PERSON = Tourist.builder()
            .lastName("Случайный")
            .firstName("Человек")
            .build();

    private static final Waypoint MUSEUM = Waypoint.builder().name("Музей").price(randomPrice()).build();
    private static final Waypoint OFFICE = Waypoint.builder().name("Офис").price(randomPrice()).build();
    private static final Waypoint PRACTICE = Waypoint.builder().name("Учебная практика").price(randomPrice()).build();

    public static void main(String[] args) {
        MANAGER.addObserver(LOGGING_TRIP_OBSERVER);
        MANAGER.addObserver(EMAIL_TRIP_OBSERVER);

        var trip = MANAGER.createTrip(BUSINESS,
                LocalDateTime.now().plusDays(7),
                List.of(BUSINESSMAN),
                List.of(OFFICE));
        MANAGER.planTrip(trip.getId());

        trip = MANAGER.createTrip(TOURIST,
                LocalDateTime.now().plusDays(16),
                List.of(BUSINESSMAN, STUDENT, RANDOM_PERSON),
                List.of(MUSEUM));
        MANAGER.planTrip(trip.getId());

        trip = MANAGER.createTrip(EDUCATION,
                LocalDateTime.now().plusDays(31),
                List.of(STUDENT),
                List.of(PRACTICE));
        MANAGER.planTrip(trip.getId());

        System.out.println("\n==== НЕСУЩЕСТВУЮЩАЯ ПОЕЗДКА ====");
        MANAGER.planTrip(UUID.randomUUID());

        System.out.println("\n==== СОХРАНЁННЫЕ ПОЕЗДКИ ====");
        MANAGER.getAllTrips().forEach(System.out::println);

        System.out.println("\n==== ПОИСК ПОЕЗДКИ ПО ID ====");
        var existingId = trip.getId();
        var notExistingId = UUID.randomUUID();
        System.out.printf("%s: %s%n", existingId, MANAGER.getTrip(existingId));
        System.out.printf("%s: %s%n", notExistingId, MANAGER.getTrip(notExistingId));

        System.out.println("\n==== ОТМЕНА ПОЕЗДКИ ====");
        MANAGER.removeObserver(EMAIL_TRIP_OBSERVER);
        MANAGER.cancelTrip(existingId);
    }

    private static BigDecimal randomPrice() {
        return BigDecimal.valueOf(RND.nextInt(1000, 2500));
    }
}

package ru.vtv.hw.practical.tripscheduler;

import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.tripscheduler.trips.Trip;

import java.io.*;

import static ru.vtv.hw.practical.tripscheduler.TripFactory.createRandomTrip;

@Slf4j
public class SerializationCheck {
    private static final String FILE_NAME = "trip.ser";

    public static void main(String[] args) {
        var trip = createRandomTrip();

        saveTripToFile(trip, FILE_NAME);
        var deserializedTrip = readAndDisplayTrip(FILE_NAME);

        System.out.printf("Are trips equal? %s%n", trip.equals(deserializedTrip));
    }

    private static void saveTripToFile(Trip trip, String fileName) {
        try(var oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(trip);
        } catch (IOException e) {
            log.error("Error on saving object to file ({}): {}, {}", fileName, trip, e.getMessage());
        }
    }

    private static Trip readAndDisplayTrip(String fileName) {
        try (var ois = new ObjectInputStream(new FileInputStream(fileName))) {
            var trip = ois.readObject();
            System.out.println("Deserialized trip: " + trip);
            return (Trip) trip;
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error reading trip from file ({}): {}", fileName, e.getMessage());
        }
        return null;
    }
}

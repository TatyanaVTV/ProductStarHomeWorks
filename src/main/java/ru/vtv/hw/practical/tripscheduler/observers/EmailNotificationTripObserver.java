package ru.vtv.hw.practical.tripscheduler.observers;

import ru.vtv.hw.practical.tripscheduler.trips.Trip;

import static java.lang.String.format;

public class EmailNotificationTripObserver implements TripObserver {
    @Override
    public void onTripCreated(Trip trip) {
        sendEmail("Trip created!", format("New trip (id = %s) has been created!", trip.getId()));
    }

    @Override
    public void onTripPlanned(Trip trip) {
        sendEmail("Trip planned!", format("Trip '%s')' has successfully planned.", trip.getId()));
    }

    @Override
    public void onTripCancelled(Trip trip) {
        sendEmail("Trip cancelled!", format("Trip '%s' has been cancelled.", trip.getId()));
    }

    private void sendEmail(String subject, String message) {
        System.out.printf("[EMAIL] Subject: %s, Message: %s%n", subject, message);
    }
}

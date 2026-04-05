package ru.vtv.hw.practical.smartliving;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String message) {
        super(message);
    }

    public static DeviceNotFoundException notConnectedToHome(SmartDevice device) {
        return new DeviceNotFoundException(String.format("Device '%s' is not attached to this SmartHome!", device));
    }
}

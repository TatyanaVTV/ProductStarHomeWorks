package ru.vtv.hw.practical.smartliving;

public class WrongStateException extends RuntimeException {
    public WrongStateException(String message) {
        super(message);
    }

    public static WrongStateException isAlreadyOn(RoomType roomType, String deviceName) {
        return new WrongStateException(String.format("%s in %s is already ON!", deviceName, roomType.name()));
    }

    public static WrongStateException isAlreadyOff(RoomType roomType, String deviceName) {
        return new WrongStateException(String.format("%s in %s is already OFF!", deviceName, roomType.name()));
    }
}

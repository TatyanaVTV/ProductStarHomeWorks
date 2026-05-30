package ru.vtv.hw.practical.oop.domain;

public class WrongDataException extends RuntimeException {

    private WrongDataException(String message) {
        super(message);
    }

    public static WrongDataException saveDataWithoutId() {
        return new WrongDataException("Record should have ID to be saved!");
    }
}

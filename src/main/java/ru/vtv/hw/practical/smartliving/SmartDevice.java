package ru.vtv.hw.practical.smartliving;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class SmartDevice {
    private final String name;
    private final RoomType room;
    private boolean isOn;

    public final void turnOn() {
        isOn = true;
    }

    public final void turnOff() {
        isOn = false;
    }

    public abstract String getStatus();
}

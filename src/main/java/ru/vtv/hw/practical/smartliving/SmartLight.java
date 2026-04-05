package ru.vtv.hw.practical.smartliving;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SmartLight extends SmartDevice implements Controllable {
    public static final int DEFAULT_BRIGHTNESS = 50;

    @Builder.Default
    private int brightness = DEFAULT_BRIGHTNESS;

    @Override
    public void increaseValue() {
        brightness++;
    }

    @Override
    public void decreaseValue() {
        brightness--;
    }

    @Override
    public String getStatus() {
        return String.format("[%s-%s] Current brightness: %s", getRoom(), getName(), brightness);
    }

    @Override
    public int getDefaultValue() {
        return DEFAULT_BRIGHTNESS;
    }
}

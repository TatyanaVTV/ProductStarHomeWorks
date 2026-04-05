package ru.vtv.hw.practical.smartliving;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SmartTV extends SmartDevice implements Controllable {
    public static final int DEFAULT_CHANNEL = 1;

    @Builder.Default
    private int channel = DEFAULT_CHANNEL;

    @Override
    public void increaseValue() {
        channel++;
    }

    @Override
    public void decreaseValue() {
        channel--;
    }

    @Override
    public String getStatus() {
        return String.format("[%s-%s] Current channel: %s", getRoom(), getName(), channel);
    }

    @Override
    public int getDefaultValue() {
        return DEFAULT_CHANNEL;
    }
}

package ru.vtv.hw.practical.smartliving;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SmartThermostat extends SmartDevice implements Controllable {
    public static final int DEFAULT_TEMPERATURE = 20;

    @Builder.Default
    private int temperature = DEFAULT_TEMPERATURE;

    @Override
    public void increaseValue() {
        temperature++;
    }

    @Override
    public void decreaseValue() {
        temperature--;
    }

    @Override
    public String getStatus() {
        return String.format("[%s-%s] Current temperature: %s", getRoom(), getName(), temperature);
    }

    @Override
    public int getDefaultValue() {
        return DEFAULT_TEMPERATURE;
    }
}

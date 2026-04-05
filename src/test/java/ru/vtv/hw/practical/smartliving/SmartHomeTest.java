package ru.vtv.hw.practical.smartliving;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static ru.vtv.hw.practical.smartliving.RoomType.*;
import static ru.vtv.hw.practical.smartliving.SmartLight.DEFAULT_BRIGHTNESS;
import static ru.vtv.hw.practical.smartliving.SmartTV.DEFAULT_CHANNEL;
import static ru.vtv.hw.practical.smartliving.SmartThermostat.DEFAULT_TEMPERATURE;

@Slf4j
@TestInstance(PER_CLASS)
public class SmartHomeTest {
    private SmartHome smartHome;

    private static final SmartLight LIGHT = SmartLight.builder().room(LIVING_ROOM).name("Living Room Light").build();
    private static final SmartTV TV = SmartTV.builder().room(BEDROOM).name("Bedroom TV").build();
    private static final SmartThermostat THERMOSTAT = SmartThermostat.builder().room(KITCHEN).name("Kitchen Thermostat").build();
    private static final SmartLight GARAGE_LIGHT = SmartLight.builder().room(GARAGE).name("Garage Light").build();

    private static final String INITIAL_COUNT_ERROR = "Количество устройств в подготовленном умном дома = 3!";
    private static final String NOT_CONNECTED_ERROR =
            "Должна быть ошибка при попытке взаимодействия с устройством, не подключенным к данному умному дому!";
    private static final String TURNED_OFF_BY_DEFAULT_ERROR = "Устройство по умолчанию должно быть выключено!";
    private static final String NOT_TURNED_ON_ERROR = "Устройство должно быть включено после turnOn!";
    private static final String NOT_TURNED_OFF_ERROR = "Устройство должно быть выключено после turnOff!";

    @BeforeEach
    void initialSetup() {
        smartHome = new SmartHome();

        LIGHT.turnOff();
        TV.turnOff();
        THERMOSTAT.turnOff();
        GARAGE_LIGHT.turnOff();

        smartHome.addDevice(LIGHT);
        smartHome.addDevice(TV);
        smartHome.addDevice(THERMOSTAT);
    }

    @Test
    public void testAddDevice() {
        var initialCount = smartHome.getTotalDevicesCount();

        smartHome.addDevice(GARAGE_LIGHT);
        assertEquals(3, initialCount, INITIAL_COUNT_ERROR);
        assertEquals(initialCount + 1, smartHome.getTotalDevicesCount(),
                "Общее количество устройств умного дома должно было увеличиться на единицу!");
    }

    @Test
    public void testRemoveDevice_connectedToHome() {
        var initialDevicesCount = smartHome.getTotalDevicesCount();

        smartHome.removeDevice(TV);

        assertEquals(initialDevicesCount - 1, smartHome.getTotalDevicesCount(),
                "Общее количество устройств умного дома должно было уменьшиться на единицу!");
        var exception = assertThrows(DeviceNotFoundException.class,
                () -> smartHome.getDeviceStatus(TV), NOT_CONNECTED_ERROR);
        assertTrue(exception.getMessage().contains("is not attached to this SmartHome"));
    }

    @Test
    public void testRemoveDevice_notConnectedToHome() {
        var exception = assertThrows(DeviceNotFoundException.class,
                () -> smartHome.removeDevice(GARAGE_LIGHT), NOT_CONNECTED_ERROR);
        assertTrue(exception.getMessage().contains("is not attached to this SmartHome"));
    }

    @Test
    void testTurnOnDevice_connectedToHome() {
        var initialState = LIGHT.isOn();

        smartHome.turnOn(LIGHT);

        assertFalse(initialState, TURNED_OFF_BY_DEFAULT_ERROR);
        assertTrue(LIGHT.isOn(), NOT_TURNED_ON_ERROR);
    }

    @Test
    void testTurnOnDevice_notConnectedToHome() {
        var exception = assertThrows(DeviceNotFoundException.class, () -> smartHome.turnOn(GARAGE_LIGHT), NOT_CONNECTED_ERROR);
        assertTrue(exception.getMessage().contains("is not attached to this SmartHome"));
    }

    @Test
    void testTurnOffDevice_connectedToHome() {
        var initialState = LIGHT.isOn();

        smartHome.turnOn(LIGHT);
        var turningOnState = LIGHT.isOn();

        smartHome.turnOff(LIGHT);

        assertFalse(initialState, TURNED_OFF_BY_DEFAULT_ERROR);
        assertTrue(turningOnState, NOT_TURNED_ON_ERROR);
        assertFalse(LIGHT.isOn(), NOT_TURNED_OFF_ERROR);
    }

    @Test
    void testTurnOffDevice_notConnectedToHome() {
        var exception = assertThrows(DeviceNotFoundException.class, () -> smartHome.turnOn(GARAGE_LIGHT), NOT_CONNECTED_ERROR);
        assertTrue(exception.getMessage().contains("is not attached to this SmartHome"));
    }

    @Test
    void testGetActiveDevices() {
        assertThat(smartHome.getActiveDevicesCount())
                .as("Изначально все устройства умного дома должны быть выключены!")
                .isEqualTo(0);

        smartHome.turnOn(TV);
        assertThat(smartHome.getActiveDevicesCount())
                .as("Общее количество активных устройств умного дома должно было увеличиться на единицу!")
                .isEqualTo(1);

        smartHome.turnOn(LIGHT);
        assertThat(smartHome.getActiveDevicesCount())
                .as("Общее количество активных устройств умного дома должно было увеличиться на единицу!")
                .isEqualTo(2);

        assertThatThrownBy(() -> smartHome.turnOn(GARAGE_LIGHT)).isInstanceOf(DeviceNotFoundException.class);
        assertThat(smartHome.getActiveDevicesCount())
                .as("Общее количество активных устройств умного дома не должно было измениться!")
                .isEqualTo(2);

        smartHome.turnOff(TV);
        assertThat(smartHome.getActiveDevicesCount())
                .as("Общее количество активных устройств умного дома должно было уменьшиться на единицу!")
                .isEqualTo(1);
    }

    @ParameterizedTest
    @MethodSource("provideDevices")
    public <T extends SmartDevice & Controllable> void testDeviceParamChanging(T device, String paramName, int defaultValue) {
        assertThat(device.getDefaultValue())
                .as(format("Изначальное значение на устройстве должно быть равно %d!", defaultValue))
                .isEqualTo(defaultValue);

        device.increaseValue();
        assertThat(smartHome.getDeviceStatus(device))
                .as("Значение параметра устройства должно было увеличиться на единицу!")
                .contains(format("Current %s: %d", paramName, device.getDefaultValue() + 1));

        device.decreaseValue();
        assertThat(smartHome.getDeviceStatus(device))
                .as("Значение параметра устройства должно было уменьшиться на единицу!")
                .contains(format("Current %s: %d", paramName, device.getDefaultValue()));
    }

    private static Stream<Arguments> provideDevices() {
        return Stream.of(
                Arguments.of(LIGHT, "brightness", DEFAULT_BRIGHTNESS),
                Arguments.of(TV, "channel", DEFAULT_CHANNEL),
                Arguments.of(THERMOSTAT, "temperature", DEFAULT_TEMPERATURE)
        );
    }
}

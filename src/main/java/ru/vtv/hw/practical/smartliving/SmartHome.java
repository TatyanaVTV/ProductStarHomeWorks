package ru.vtv.hw.practical.smartliving;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.smartliving.DeviceNotFoundException.notConnectedToHome;
import static ru.vtv.hw.practical.smartliving.WrongStateException.isAlreadyOff;
import static ru.vtv.hw.practical.smartliving.WrongStateException.isAlreadyOn;

@Slf4j
public class SmartHome {
    private final Map<String, SmartDevice> deviceMap = new HashMap<>();

    public void addDevice(SmartDevice device) {
        log.debug("Adding device {}", device);
        var key = defineKey(device);
        deviceMap.put(key, device);
        HomeStats.incrementTotalDevices(this);
    }

    public void removeDevice(SmartDevice device) {
        log.debug("Removing device {}", device);
        var key = defineKey(device);

        var deviceInHome = deviceMap.get(key);
        if (isNull(deviceInHome)) throw notConnectedToHome(device);
        deviceMap.remove(key);

        HomeStats.decrementTotalDevices(this);
    }

    public String getDeviceStatus(SmartDevice device) {
        log.debug("Get device status {}", device);
        var key = defineKey(device);

        var deviceInHome = deviceMap.get(key);
        if (isNull(deviceInHome)) throw notConnectedToHome(device);

        return deviceInHome.getStatus();
    }

    public void turnOn(SmartDevice device) {
        log.debug("Turning on device {}", device);
        var deviceFromHome = deviceMap.get(defineKey(device));

        if (isNull(deviceFromHome)) throw notConnectedToHome(device);
        if (deviceFromHome.isOn()) throw isAlreadyOn(deviceFromHome.getRoom(), deviceFromHome.getName());

        deviceFromHome.turnOn();
        HomeStats.incrementActiveDevices(this);
    }

    public void turnOff(SmartDevice device) {
        var deviceFromHome = deviceMap.get(defineKey(device));

        if (isNull(deviceFromHome)) throw notConnectedToHome(device);
        if (!deviceFromHome.isOn()) throw isAlreadyOff(deviceFromHome.getRoom(), deviceFromHome.getName());

        deviceFromHome.turnOff();
        HomeStats.decrementActiveDevices(this);
    }

    public int getTotalDevicesCount() {
        return HomeStats.totalDevices.getOrDefault(this, 0);
    }

    public int getActiveDevicesCount() {
        return HomeStats.activeDevices.getOrDefault(this, 0);
    }

    private String defineKey(SmartDevice device) {
        return defineKey(device.getRoom(), device.getName());
    }

    private String defineKey(RoomType roomType, String name) {
        return roomType.name() + "_" + name;
    }

    public static class HomeStats {
        private static final Map<SmartHome, Integer> totalDevices = new ConcurrentHashMap<>();
        private static final Map<SmartHome, Integer> activeDevices = new ConcurrentHashMap<>();

        public static void incrementTotalDevices(SmartHome smartHome) {
            totalDevices.merge(smartHome, 1, Integer::sum);
        }

        public static void decrementTotalDevices(SmartHome smartHome) {
            totalDevices.compute(smartHome,
                    (_, currentCount) -> currentCount == null ? 0 : Math.max(0, currentCount - 1));
        }

        public static void incrementActiveDevices(SmartHome smartHome) {
            activeDevices.merge(smartHome, 1, Integer::sum);
        }

        public static void decrementActiveDevices(SmartHome smartHome) {
            activeDevices.compute(smartHome,
                    (_, currentCount) -> currentCount == null ? 0 : Math.max(0, currentCount - 1));
        }
    }
}

package ru.vtv.hw.practical.tripscheduler;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Builder
public class Trip {
    private LocalDateTime tripDate;
    private int nights;

    @Builder.Default
    private List<Tourist> touristsList = new ArrayList<>();

    @Builder.Default
    private List<Waypoint> waypointsList = new ArrayList<>();
}

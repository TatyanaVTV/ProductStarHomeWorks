package ru.vtv.hw.practical.tripscheduler.trips;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.tripscheduler.Tourist;
import ru.vtv.hw.practical.tripscheduler.Waypoint;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.vtv.hw.practical.tripscheduler.trips.TripType.NOT_DEFINED;

@Slf4j
@Data
@SuperBuilder
public abstract class Trip implements Serializable, Planable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Builder.Default
    private final UUID id = UUID.randomUUID();

    @Builder.Default
    private TripType tripType = NOT_DEFINED;

    private LocalDateTime tripDate;
    private int nights;

    @Builder.Default
    @JsonIgnore
    transient private LocalDateTime creationTime = LocalDateTime.now();

    @Builder.Default
    private List<Tourist> touristsList = new ArrayList<>();

    @Builder.Default
    private List<Waypoint> waypointsList = new ArrayList<>();
}

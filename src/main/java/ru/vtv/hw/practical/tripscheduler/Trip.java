package ru.vtv.hw.practical.tripscheduler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Builder
public class Trip implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

package ru.vtv.hw.practical.tripscheduler;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class Waypoint {
    private UUID id;
    private String name;
    private BigDecimal price;
}

package ru.vtv.hw.practical.tripscheduler;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import static java.math.RoundingMode.HALF_UP;

@Data
@Builder
public class Waypoint implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Builder.Default
    private final UUID id = UUID.randomUUID();

    private String name;
    private BigDecimal price;

    private Waypoint(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price.setScale(2, HALF_UP);
    }
}

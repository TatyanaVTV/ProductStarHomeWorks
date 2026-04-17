package ru.vtv.hw.practical.tripscheduler;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Tourist {
    private UUID id;
    private String firstName;
    private String lastName;
}

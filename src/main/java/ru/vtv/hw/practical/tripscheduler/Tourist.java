package ru.vtv.hw.practical.tripscheduler;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class Tourist implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String firstName;
    private String lastName;
}

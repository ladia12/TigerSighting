package com.tigerhall.model;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TigerInput {
    private String name;
    private LocalDate dateOfBirth;
    private LocalDateTime lastSeenTimestamp;
    private Double lastSeenLatitude;
    private Double lastSeenLongitude;
}

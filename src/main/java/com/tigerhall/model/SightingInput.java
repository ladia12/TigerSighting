package com.tigerhall.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SightingInput {
    private Long tigerId;
    private LocalDateTime timestamp;
    private Double latitude;
    private Double longitude;
    private String image;
}

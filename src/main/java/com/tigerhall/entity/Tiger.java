package com.tigerhall.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Tiger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private LocalDateTime lastSeenTimestamp;

    @Column(nullable = false)
    private Double lastSeenLatitude;

    @Column(nullable = false)
    private Double lastSeenLongitude;
}


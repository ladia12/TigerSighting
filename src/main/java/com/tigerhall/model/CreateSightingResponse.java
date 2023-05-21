package com.tigerhall.model;

import com.tigerhall.entity.Sighting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateSightingResponse {
    private Sighting sighting;
    private String errorMessage;
}

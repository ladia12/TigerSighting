package com.tigerhall.model;

import com.tigerhall.entity.Tiger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTigerResponse {
    private Tiger tiger;
    private String errorMessage;
}

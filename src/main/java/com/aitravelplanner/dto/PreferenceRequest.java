package com.aitravelplanner.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record PreferenceRequest(
        @NotBlank String destination,
        @Min(1) double budget,
        @Min(1) int days,
        @NotBlank String travelType,
        List<String> interests,
        @NotBlank String season
) {}

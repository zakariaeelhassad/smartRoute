package org.example.smartroute.entities.DTO.warehouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalTime;

public record CreateWarehouseDto(
        @NotBlank String name,
        @NotBlank String address,
        @NotNull Double latitude,
        @NotNull Double longitude,
        @NotNull LocalTime openingTime,
        @NotNull LocalTime closingTime
) {
}

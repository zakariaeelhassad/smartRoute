package org.example.smartroute.entities.DTO.warehouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record WarehouseDto(
        @NotNull Long id,
        @NotBlank String name,
        @NotBlank String address,
        @NotNull Double latitude,
        @NotNull Double longitude,
        @NotNull LocalTime openingTime,
        @NotNull LocalTime closingTime
) {
}

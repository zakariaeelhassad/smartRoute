package org.example.smartroute.entities.DTO.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.smartroute.entities.enums.VehicleType;

public record CreateVehicleDto(
        @NotBlank String name ,
        @NotNull @Positive Double capacityWeight,
        @NotNull @Positive Double capacityVolume,
        @NotNull @Positive Integer maxDeliveries,
        @NotNull VehicleType vehicleType
) {
}

package org.example.smartroute.entities.DTO.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.smartroute.entities.enums.VehicleType;

public record VehicleDto(
        @Positive Long id ,
        @NotBlank String name ,
        @NotNull Double capacityWeight ,
        @NotNull Double capacityVolume ,
        @NotNull Integer maxDeliveries,
        VehicleType vehicleType
) {
}

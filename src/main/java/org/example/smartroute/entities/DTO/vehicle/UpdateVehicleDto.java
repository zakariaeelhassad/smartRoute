package org.example.smartroute.entities.DTO.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.smartroute.entities.enums.VehicleType;

public record UpdateVehicleDto(
        String name ,
        Double capacityWeight,
        Double capacityVolume,
        Integer maxDeliveries,
        VehicleType vehicleType
) {
}

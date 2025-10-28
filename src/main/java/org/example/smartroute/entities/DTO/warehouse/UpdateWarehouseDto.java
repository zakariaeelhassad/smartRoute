package org.example.smartroute.entities.DTO.warehouse;

import java.time.LocalTime;

public record UpdateWarehouseDto(
        String name,
        String address,
        Double latitude,
        Double longitude,
        LocalTime openingTime,
        LocalTime closingTime
) {
}

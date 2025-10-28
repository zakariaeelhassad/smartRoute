package org.example.smartroute.entities.DTO.delivery;

import org.example.smartroute.entities.enums.DeliveryStatus;
import org.example.smartroute.entities.models.Tour;

public record UpdateDeliveryDto(
        String address,
        Double latitude,
        Double longitude,
        Double weight,
        Double volume,
        String timeWindow,
        DeliveryStatus deliveryStatus,
        Tour tour
) {
}

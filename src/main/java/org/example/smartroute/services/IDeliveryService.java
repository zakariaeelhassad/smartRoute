package org.example.smartroute.services;

import org.example.smartroute.entities.DTO.delivery.CreateDeliveryDto;
import org.example.smartroute.entities.DTO.delivery.DeliveryDto;
import org.example.smartroute.entities.DTO.delivery.UpdateDeliveryDto;

import java.util.List;

public interface IDeliveryService {

    DeliveryDto create(CreateDeliveryDto deliveryDto);
    DeliveryDto update(Long id , UpdateDeliveryDto deliveryDto);
    void delete(Long id );
    DeliveryDto getById(Long id);
    List<DeliveryDto> getAll();
}

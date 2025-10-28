package org.example.smartroute.mappers;

import org.example.smartroute.entities.DTO.delivery.CreateDeliveryDto;
import org.example.smartroute.entities.DTO.delivery.DeliveryDto;
import org.example.smartroute.entities.models.Delivery;
import org.example.smartroute.mappers.api.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryMapper extends GenericMapper {

     @Mapping(target = "id", ignore = true)
     Delivery toEntity(CreateDeliveryDto dto);

     DeliveryDto toDto(Delivery delivery);
}

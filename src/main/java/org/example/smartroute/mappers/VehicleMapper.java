package org.example.smartroute.mappers;

import org.example.smartroute.entities.DTO.vehicle.CreateVehicleDto;
import org.example.smartroute.entities.DTO.vehicle.VehicleDto;
import org.example.smartroute.entities.models.Vehicle;
import org.example.smartroute.mappers.api.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper extends GenericMapper<Vehicle , VehicleDto> {

    @Mapping(target = "id", ignore = true)
    Vehicle toEntity(CreateVehicleDto dto);

    VehicleDto toDto(Vehicle vehicle);
}

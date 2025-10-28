package org.example.smartroute.services;

import org.example.smartroute.entities.DTO.vehicle.CreateVehicleDto;
import org.example.smartroute.entities.DTO.vehicle.UpdateVehicleDto;
import org.example.smartroute.entities.DTO.vehicle.VehicleDto;

import java.util.List;

public interface IVehicleService {

    VehicleDto create(CreateVehicleDto vehicleDto);
    VehicleDto update(UpdateVehicleDto vehicleDto , Long id );
    void delete(Long id);
    VehicleDto getById(Long id);
    List<VehicleDto> getAll();

}

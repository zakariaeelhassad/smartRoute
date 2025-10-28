package org.example.smartroute.services;

import org.example.smartroute.entities.DTO.warehouse.CreateWarehouseDto;
import org.example.smartroute.entities.DTO.warehouse.UpdateWarehouseDto;
import org.example.smartroute.entities.DTO.warehouse.WarehouseDto;

import java.util.List;

public interface IWarehouseService {

    WarehouseDto create(CreateWarehouseDto warehouseDto);
    WarehouseDto update(UpdateWarehouseDto warehouseDto , Long id);
    void delete(Long id);
    WarehouseDto getById(Long id);
    List<WarehouseDto> getAll();
}

package org.example.smartroute.mappers;

import org.example.smartroute.entities.DTO.warehouse.CreateWarehouseDto;
import org.example.smartroute.entities.DTO.warehouse.WarehouseDto;
import org.example.smartroute.entities.models.Warehouse;
import org.example.smartroute.mappers.api.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WarehouseMapper extends GenericMapper<Warehouse , WarehouseDto> {

    @Mapping(target = "id", ignore = true)
    Warehouse toEntity(CreateWarehouseDto dto);

    WarehouseDto toDto(Warehouse Warehouse);
}

package org.example.smartroute.mappers;

import org.example.smartroute.entities.DTO.tour.TourDto;
import org.example.smartroute.entities.DTO.tour.CreateTourDto;
import org.example.smartroute.entities.DTO.tour.UpdateTourDto;
import org.example.smartroute.entities.models.Tour;
import org.example.smartroute.mappers.api.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface TourMapper extends GenericMapper<Tour, TourDto> {

    @Mapping(target = "id", ignore = true)
    Tour toEntity(CreateTourDto dto);

    TourDto toDto(Tour tour);
}

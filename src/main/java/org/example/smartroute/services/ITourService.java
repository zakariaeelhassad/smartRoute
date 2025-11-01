package org.example.smartroute.services;

import org.example.smartroute.entities.DTO.tour.TourDto;
import org.example.smartroute.entities.DTO.tour.CreateTourDto;
import org.example.smartroute.entities.DTO.tour.UpdateTourDto;

import java.util.List;

public interface ITourService {

    TourDto create(CreateTourDto tourDto);
    TourDto update(Long id , UpdateTourDto tourDto);
    void delete (Long id);
    TourDto getById(Long id);
    List<TourDto> getAll();
    void optimizeTour(Long id);
    TourDto addDeliveriesToTour(Long tourId, List<Long> deliveryIds);

}

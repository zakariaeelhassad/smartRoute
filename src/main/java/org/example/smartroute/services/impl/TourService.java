package org.example.smartroute.services.impl;

import org.example.smartroute.entities.DTO.tour.TourDto;
import org.example.smartroute.entities.DTO.tour.CreateTourDto;
import org.example.smartroute.entities.DTO.tour.UpdateTourDto;
import org.example.smartroute.entities.models.Tour;
import org.example.smartroute.mappers.TourMapper;
import org.example.smartroute.repositories.TourRepository;
import org.example.smartroute.services.ITourService;
import org.example.smartroute.utils.ClarkeWrightOptimizer;
import org.example.smartroute.utils.NearestNeighborOptimizer;

import java.util.List;
import java.util.stream.Collectors;

public class TourService implements ITourService {
    private final TourRepository repository;
    private final TourMapper tourMapper;
    private final NearestNeighborOptimizer nearestNeighborOptimizer ;
    private final ClarkeWrightOptimizer clarkeWrightOptimizer;

    public TourService(TourRepository repository, TourMapper tourMapper , NearestNeighborOptimizer nearestNeighborOptimizer , ClarkeWrightOptimizer clarkeWrightOptimizer) {
        this.repository = repository;
        this.tourMapper = tourMapper;
        this.nearestNeighborOptimizer = nearestNeighborOptimizer;
        this.clarkeWrightOptimizer = clarkeWrightOptimizer;
    }

    @Override
    public TourDto create(CreateTourDto tourDto) {
        Tour tour = tourMapper.toEntity(tourDto);
        Tour saveTour = repository.save(tour);
        return tourMapper.toDto(saveTour);
    }

    @Override
    public TourDto update(Long id, UpdateTourDto tourDto) {
        Tour tour = repository.findById(id).orElseThrow(() -> new RuntimeException("Tour not found"));
        tour.setDate(tourDto.date());
        tour.setTotalDistance(tourDto.totalDistance());
        tour.setAlgorithmType(tourDto.algorithmType());
        tour.setWarehouse(tourDto.warehouse());
        tour.setVehicle(tourDto.vehicle());
        Tour updateTour = repository.save(tour);
        return tourMapper.toDto(updateTour);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public TourDto getById(Long id) {
        Tour tour = repository.findById(id).orElseThrow(() -> new RuntimeException("Tour not found"));
        return tourMapper.toDto(tour);
    }

    @Override
    public List<TourDto> getAll() {
        List<TourDto> tours = repository.findAll().stream().map(tourMapper::toDto).collect(Collectors.toList());
        return tours;
    }

    public void optimizeTour(Long id){
        Tour tour = repository.findById(id).orElseThrow(()-> new RuntimeException("Tour not found"));

        if(tour.getDeliveries() == null || tour.getDeliveries().isEmpty()){
            throw new RuntimeException("No deliveries to optimize for this tour");
        }

        switch (tour.getAlgorithmType()) {
            case NEAREST_NEIGHBOR -> tour.setDeliveries(
                    nearestNeighborOptimizer.calculateOptimalTour(tour)
            );
            case CLARKE_WRIGHT -> tour.setDeliveries(
                    clarkeWrightOptimizer.calculateOptimalTour(tour)
            );
        }
        repository.save(tour);
    }
}
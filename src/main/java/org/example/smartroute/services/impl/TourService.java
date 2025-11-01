package org.example.smartroute.services.impl;

import org.example.smartroute.entities.DTO.tour.CreateTourDto;
import org.example.smartroute.entities.DTO.tour.TourDto;
import org.example.smartroute.entities.DTO.tour.UpdateTourDto;
import org.example.smartroute.entities.models.Delivery;
import org.example.smartroute.entities.models.Tour;
import org.example.smartroute.mappers.TourMapper;
import org.example.smartroute.repositories.DeliveryRepository;
import org.example.smartroute.repositories.TourRepository;
import org.example.smartroute.services.ITourService;
import org.example.smartroute.utils.DistanceCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TourService implements ITourService {

    private final TourRepository tourRepository;
    private final DeliveryRepository deliveryRepository;
    private final TourMapper tourMapper;
    private final NearestNeighborOptimizer nearestNeighborOptimizer;
    private final ClarkeWrightOptimizer clarkeWrightOptimizer;
    private final DistanceCalculator distanceCalculator;

    public TourService(
            TourRepository tourRepository,
            DeliveryRepository deliveryRepository,
            TourMapper tourMapper,
            NearestNeighborOptimizer nearestNeighborOptimizer,
            ClarkeWrightOptimizer clarkeWrightOptimizer,
            DistanceCalculator distanceCalculator
    ) {
        this.tourRepository = tourRepository;
        this.deliveryRepository = deliveryRepository;
        this.tourMapper = tourMapper;
        this.nearestNeighborOptimizer = nearestNeighborOptimizer;
        this.clarkeWrightOptimizer = clarkeWrightOptimizer;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public TourDto create(CreateTourDto dto) {
        Tour tour = tourMapper.toEntity(dto);
        if (tour.getDeliveries() != null) {
            tour.getDeliveries().forEach(d -> d.setTour(tour));
        }
        Tour saved = tourRepository.save(tour);
        return tourMapper.toDto(saved);
    }

    @Override
    public TourDto update(Long id, UpdateTourDto dto) {
        Tour existing = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        existing.setDate(dto.date());
        existing.setTotalDistance(dto.totalDistance());
        existing.setAlgorithmType(dto.algorithmType());
        existing.setWarehouse(dto.warehouse());
        existing.setVehicle(dto.vehicle());

        if (dto.deliveries() != null) {
            existing.getDeliveries().clear();
            existing.getDeliveries().addAll(dto.deliveries());
            existing.getDeliveries().forEach(d -> d.setTour(existing));
        }

        Tour updated = tourRepository.save(existing);
        return tourMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        tourRepository.deleteById(id);
    }

    @Override
    public TourDto getById(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        if (tour.getDeliveries() != null && !tour.getDeliveries().isEmpty()) {
            List<Delivery> optimized = optimizeList(tour);
            tour.setDeliveries(optimized);
        }

        return tourMapper.toDto(tour);
    }

    @Override
    public List<TourDto> getAll() {
        return tourRepository.findAll().stream()
                .peek(tour -> {
                    if (tour.getDeliveries() != null && !tour.getDeliveries().isEmpty()) {
                        List<Delivery> optimized = optimizeList(tour);
                        tour.setDeliveries(optimized);
                    }
                })
                .map(tourMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void optimizeTour(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        if (tour.getDeliveries() == null || tour.getDeliveries().isEmpty()) {
            throw new RuntimeException("No deliveries to optimize for this tour");
        }

        List<Delivery> optimized = optimizeList(tour);
        tour.setDeliveries(optimized);
        tourRepository.save(tour);
    }

    public TourDto addDeliveriesToTour(Long tourId, List<Long> deliveryIds) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        List<Delivery> deliveries = deliveryRepository.findAllById(deliveryIds);

        deliveries.forEach(d -> d.setTour(tour));
        tour.getDeliveries().addAll(deliveries);

        Tour saved = tourRepository.save(tour);
        return tourMapper.toDto(saved);
    }

    private List<Delivery> optimizeList(Tour tour) {
        if (tour.getAlgorithmType() == null) return tour.getDeliveries();

        List<Delivery> optimized = switch (tour.getAlgorithmType()) {
            case NEAREST_NEIGHBOR -> nearestNeighborOptimizer.optimizerTour(tour);
            case CLARKE_WRIGHT -> clarkeWrightOptimizer.optimizerTour(tour);
        };

        double totalDistance = 0.0;

        if (!optimized.isEmpty()) {
            totalDistance += distanceCalculator.distance(tour.getWarehouse(), optimized.get(0));
        }

        for (int i = 0; i < optimized.size() - 1; i++) {
            totalDistance += distanceCalculator.distance(optimized.get(i), optimized.get(i + 1));
        }

        if (!optimized.isEmpty()) {
            totalDistance += distanceCalculator.distance(tour.getWarehouse(), optimized.get(optimized.size() - 1));
        }

        tour.setTotalDistance(totalDistance);

        return optimized;
    }
}

package org.example.smartroute.repositories;

import org.example.smartroute.entities.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
}

package org.example.smartroute.repositories;

import org.example.smartroute.entities.models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}

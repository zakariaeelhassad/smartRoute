package org.example.smartroute.repositories;

import org.example.smartroute.entities.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse , Long> {
}

package org.example.smartroute.services.impl;

import org.example.smartroute.entities.models.Delivery;
import org.example.smartroute.entities.models.Tour;
import org.example.smartroute.entities.models.Warehouse;
import org.example.smartroute.services.TourOptimizer;
import org.example.smartroute.utils.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Nearest Neighbor optimizer implementation.
 *
 * Principe :
 *  - Démarrer depuis l'entrepôt (warehouse)
 *  - À chaque étape, aller au client non-visité le plus proche
 *  - Continuer jusqu'à avoir visité tous les clients
 *
 * Retour :
 *  - Liste ordonnée des Delivery (ordre de visite)
 */
public class NearestNeighborOptimizer implements TourOptimizer {

    private final DistanceCalculator distanceCalculator;

    public NearestNeighborOptimizer(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = Objects.requireNonNull(distanceCalculator, "distanceCalculator must not be null");
    }

    @Override
    public List<Delivery> optimizerTour(Tour tour) {
        if (tour == null) return List.of();
        Warehouse warehouse = tour.getWarehouse();
        List<Delivery> originals = tour.getDeliveries();

        if (originals == null || originals.isEmpty()) return List.of();
        if (warehouse == null) {
            // If no warehouse, fallback: return original order (or you can throw)
            return new ArrayList<>(originals);
        }

        // Copy to a mutable list of remaining deliveries
        List<Delivery> remaining = new ArrayList<>(originals);
        List<Delivery> ordered = new ArrayList<>();

        // Current location starts at warehouse
        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        while (!remaining.isEmpty()) {
            Delivery nearest = null;
            double minDistance = Double.MAX_VALUE;

            for (Delivery d : remaining) {
                // guard against null coordinates
                if (d == null || d.getLatitude() == null || d.getLongitude() == null) continue;

                double dist = distanceCalculator.distance(currentLat, currentLon, d.getLatitude(), d.getLongitude());
                if (dist < minDistance) {
                    minDistance = dist;
                    nearest = d;
                }
            }

            if (nearest == null) break; // nothing valid left

            ordered.add(nearest);
            remaining.remove(nearest);

            // move current location to the chosen delivery
            currentLat = nearest.getLatitude();
            currentLon = nearest.getLongitude();
        }

        return ordered;
    }
}

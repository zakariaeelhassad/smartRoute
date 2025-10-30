package org.example.smartroute.utils;

import org.example.smartroute.entities.models.Delivery;
import org.example.smartroute.entities.models.Tour;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborOptimizer {

    public List<Delivery> calculateOptimalTour(Tour tour) {
        List<Delivery> deliveries = new ArrayList<>(tour.getDeliveries());
        List<Delivery> ordered = new ArrayList<>();
        Delivery current = null;

        while (!deliveries.isEmpty()) {
            Delivery next = findNearest(current, deliveries);
            ordered.add(next);
            deliveries.remove(next);
            current = next;
        }

        return ordered;
    }

    private Delivery findNearest(Delivery current, List<Delivery> deliveries) {
        Delivery nearest = deliveries.get(0);
        double minDistance = Double.MAX_VALUE;

        for (Delivery delivery : deliveries) {
            double distance = calculateDistance(current, delivery);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = delivery;
            }
        }
        return nearest;
    }

    private double calculateDistance(Delivery d1, Delivery d2) {
        if (d1 == null || d2 == null) return 0;
        double dx = d1.getLatitude() - d2.getLatitude();
        double dy = d1.getLongitude() - d2.getLongitude();
        return Math.sqrt(dx * dx + dy * dy);
    }
}

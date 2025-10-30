package org.example.smartroute.utils;

import org.example.smartroute.entities.models.Delivery;
import org.example.smartroute.entities.models.Tour;

import java.util.*;

public class ClarkeWrightOptimizer {

    public List<Delivery> calculateOptimalTour(Tour tour) {
        List<Delivery> deliveries = new ArrayList<>(tour.getDeliveries());
        if (deliveries.size() <= 1) return deliveries;

        Delivery warehouse = null;

        Map<String, Double> distanceMap = new HashMap<>();
        for (Delivery d1 : deliveries) {
            for (Delivery d2 : deliveries) {
                if (!d1.equals(d2)) {
                    distanceMap.put(key(d1, d2), calculateDistance(d1, d2));
                }
            }
        }

        List<Saving> savings = new ArrayList<>();
        for (Delivery i : deliveries) {
            for (Delivery j : deliveries) {
                if (!i.equals(j)) {
                    double dWi = distanceToWarehouse(i, tour);
                    double dWj = distanceToWarehouse(j, tour);
                    double dij = distanceMap.get(key(i, j));
                    double saving = dWi + dWj - dij;
                    savings.add(new Saving(i, j, saving));
                }
            }
        }

        savings.sort((a, b) -> Double.compare(b.value, a.value));

        LinkedHashSet<Delivery> orderedTour = new LinkedHashSet<>();
        for (Saving s : savings) {
            orderedTour.add(s.i);
            orderedTour.add(s.j);
        }

        return new ArrayList<>(orderedTour);
    }

    private double distanceToWarehouse(Delivery d, Tour tour) {
        if (tour.getWarehouse() == null) return 0;
        double dx = d.getLatitude() - tour.getWarehouse().getLatitude();
        double dy = d.getLongitude() - tour.getWarehouse().getLongitude();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double calculateDistance(Delivery d1, Delivery d2) {
        double dx = d1.getLatitude() - d2.getLatitude();
        double dy = d1.getLongitude() - d2.getLongitude();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private String key(Delivery d1, Delivery d2) {
        return d1.getId() + "-" + d2.getId();
    }

    private static class Saving {
        Delivery i;
        Delivery j;
        double value;

        Saving(Delivery i, Delivery j, double value) {
            this.i = i;
            this.j = j;
            this.value = value;
        }
    }
}

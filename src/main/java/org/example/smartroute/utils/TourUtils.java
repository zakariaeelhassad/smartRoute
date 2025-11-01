package org.example.smartroute.utils;

import org.example.smartroute.entities.models.Delivery;
import org.example.smartroute.entities.models.Warehouse;

import java.util.List;

public class TourUtils {
    private TourUtils(){}

    public static double calculateTotalDistance(Warehouse warehouse, List<Delivery> deliveries, DistanceCalculator distanceCalculator){
        if(warehouse == null || deliveries == null || deliveries.isEmpty()) return 0;

        double totalDistance = 0;
        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        for (Delivery d : deliveries){
            totalDistance += distanceCalculator.distance(currentLat, currentLon, d.getLatitude(), d.getLongitude());
            currentLat = d.getLatitude();
            currentLon = d.getLongitude();
        }

        totalDistance += distanceCalculator.distance(currentLat, currentLon, warehouse.getLatitude(), warehouse.getLongitude());

        return totalDistance;
    }
}

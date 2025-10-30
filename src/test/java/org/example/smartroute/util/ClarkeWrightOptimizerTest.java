package org.example.smartroute.util;

import org.example.smartroute.entities.models.Delivery;
import org.example.smartroute.entities.models.Tour;
import org.example.smartroute.entities.models.Warehouse;
import org.example.smartroute.utils.ClarkeWrightOptimizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClarkeWrightOptimizerTest {
    private ClarkeWrightOptimizer optimizer;
    private Tour tour;

    @BeforeEach
    void setup() {
        optimizer = new ClarkeWrightOptimizer();

        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setLatitude(0.0);
        warehouse.setLongitude(0.0);

        Delivery d1 = new Delivery();
        d1.setId(1L);
        d1.setLatitude(1.0);
        d1.setLongitude(1.0);

        Delivery d2 = new Delivery();
        d2.setId(2L);
        d2.setLatitude(2.0);
        d2.setLongitude(2.0);

        Delivery d3 = new Delivery();
        d3.setId(3L);
        d3.setLatitude(3.0);
        d3.setLongitude(3.0);

        tour = new Tour();
        tour.setWarehouse(warehouse);
        tour.setDeliveries(Arrays.asList(d1, d2, d3));
    }

    @Test
    void testCalculateOptimalTour() {
        List<Delivery> result = optimizer.calculateOptimalTour(tour);

        assertEquals(3, result.size());

        long distinctCount = result.stream().map(Delivery::getId).distinct().count();
        assertEquals(3, distinctCount);

        result.forEach(d -> System.out.println("Delivery ID: " + d.getId()));
    }
}

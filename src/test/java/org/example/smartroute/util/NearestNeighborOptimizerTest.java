package org.example.smartroute.util;

import org.example.smartroute.entities.models.Delivery;
import org.example.smartroute.entities.models.Tour;
import org.example.smartroute.utils.NearestNeighborOptimizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NearestNeighborOptimizerTest {
    private NearestNeighborOptimizer optimizer;
    private Tour tour;
    private Delivery d1;
    private Delivery d2;
    private Delivery d3;

    @BeforeEach
    void setUp() {
        optimizer = new NearestNeighborOptimizer();

        d1 = new Delivery();
        d1.setId(1L);
        d1.setLatitude(0.0);
        d1.setLongitude(0.0);

        d2 = new Delivery();
        d2.setId(2L);
        d2.setLatitude(1.0);
        d2.setLongitude(1.0);

        d3 = new Delivery();
        d3.setId(3L);
        d3.setLatitude(2.0);
        d3.setLongitude(2.0);

        tour = new Tour();
        tour.setDeliveries(Arrays.asList(d1, d2, d3));
    }

    @Test
    void testCalculateOptimalTour() {
        List<Delivery> result = optimizer.calculateOptimalTour(tour);

        assertEquals(3, result.size(), "Le nombre de livraisons dans le résultat doit être 3");

        assertEquals(1L, result.get(0).getId(), "La première livraison doit être d1");
        assertEquals(2L, result.get(1).getId(), "La deuxième livraison doit être d2");
        assertEquals(3L, result.get(2).getId(), "La dernière livraison doit être d3");
    }
}

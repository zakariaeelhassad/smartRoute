package org.example.smartroute.utils;

import org.example.smartroute.entities.models.Delivery;
import org.example.smartroute.entities.models.Tour;
import org.example.smartroute.entities.models.Warehouse;
import org.example.smartroute.services.TourOptimizer;

import java.util.*;

public class ClarkeWrightOptimizer implements TourOptimizer {

    private final DistanceCalculator distanceCalculator;

    public ClarkeWrightOptimizer(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public List<Delivery> optimizerTour(Tour tour) {
        Warehouse warehouse = tour.getWarehouse();
        List<Delivery> deliveries = new ArrayList<>(tour.getDeliveries());

        if (warehouse == null || deliveries == null || deliveries.size() <= 1) {
            return deliveries != null ? deliveries : Collections.emptyList();
        }

        // Étape 1 : Calculer les économies pour chaque paire (i, j)
        List<Saving> savingsList = new ArrayList<>();
        for (int i = 0; i < deliveries.size(); i++) {
            for (int j = i + 1; j < deliveries.size(); j++) {
                Delivery di = deliveries.get(i);
                Delivery dj = deliveries.get(j);

                double dWi = distanceCalculator.distance(warehouse, di);
                double dWj = distanceCalculator.distance(warehouse, dj);
                double dij = distanceCalculator.distance(di, dj);

                double saving = dWi + dWj - dij;
                savingsList.add(new Saving(di, dj, saving));
            }
        }

        // Étape 2 : Trier les économies par ordre décroissant
        savingsList.sort((a, b) -> Double.compare(b.value, a.value));

        // Étape 3 : Construire les routes à partir des économies
        Map<Delivery, Route> deliveryToRoute = new HashMap<>();
        List<Route> routes = new ArrayList<>();

        for (Saving s : savingsList) {
            Route routeI = deliveryToRoute.get(s.i);
            Route routeJ = deliveryToRoute.get(s.j);

            // Cas 1 : ni i ni j ne sont encore dans une route → créer une nouvelle
            if (routeI == null && routeJ == null) {
                Route newRoute = new Route();
                newRoute.add(s.i);
                newRoute.add(s.j);
                routes.add(newRoute);
                deliveryToRoute.put(s.i, newRoute);
                deliveryToRoute.put(s.j, newRoute);
            }
            // Cas 2 : i est en fin de route et j est libre → ajouter j à la fin
            else if (routeI != null && routeJ == null && routeI.canAppend(s.i)) {
                routeI.add(s.j);
                deliveryToRoute.put(s.j, routeI);
            }
            // Cas 3 : j est en début de route et i est libre → ajouter i au début
            else if (routeJ != null && routeI == null && routeJ.canPrepend(s.j)) {
                routeJ.prepend(s.i);
                deliveryToRoute.put(s.i, routeJ);
            }
            // Cas 4 : fusion de deux routes (si possible)
            else if (routeI != null && routeJ != null && routeI != routeJ) {
                if (routeI.canAppend(s.i) && routeJ.canPrepend(s.j)) {
                    routeI.merge(routeJ);
                    for (Delivery d : routeJ.deliveries) {
                        deliveryToRoute.put(d, routeI);
                    }
                    routes.remove(routeJ);
                }
            }
            // Sinon → pas de fusion possible
        }

        // Étape 4 : Sélectionner la plus grande route (finale)
        Route finalRoute = routes.stream()
                .max(Comparator.comparingInt(r -> r.deliveries.size()))
                .orElseGet(() -> {
                    Route r = new Route();
                    r.deliveries.addAll(deliveries);
                    return r;
                });

        return new ArrayList<>(finalRoute.deliveries);
    }

    // 🔹 Classe interne pour représenter une route temporaire
    private static class Route {
        List<Delivery> deliveries = new ArrayList<>();

        void add(Delivery d) {
            deliveries.add(d);
        }

        void prepend(Delivery d) {
            deliveries.add(0, d);
        }

        boolean canAppend(Delivery d) {
            return deliveries.get(deliveries.size() - 1).equals(d);
        }

        boolean canPrepend(Delivery d) {
            return deliveries.get(0).equals(d);
        }

        void merge(Route other) {
            this.deliveries.addAll(other.deliveries);
        }
    }

    // 🔹 Structure pour stocker les économies entre deux clients
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

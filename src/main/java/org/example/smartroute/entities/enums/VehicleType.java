package org.example.smartroute.entities.enums;

public enum VehicleType {
    BIKE(50.0, 0.5, 15),
    VAN(1000.0, 8.0, 50),
    TRUCK(5000.0, 40.0, 100);

    private final double capacityWeight;
    private final double capacityVolume;
    private final int maxDeliveries;

    VehicleType(double capacityWeight, double capacityVolume, int maxDeliveries) {
        this.capacityWeight = capacityWeight;
        this.capacityVolume = capacityVolume;
        this.maxDeliveries = maxDeliveries;
    }

    public double getCapacityWeight() { return capacityWeight; }
    public double getCapacityVolume() { return capacityVolume; }
    public int getMaxDeliveries() { return maxDeliveries; }
}


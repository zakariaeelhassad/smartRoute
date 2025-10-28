package org.example.smartroute.entities.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.smartroute.entities.enums.AlgorithmType;
import org.example.smartroute.entities.enums.VehicleType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name" , nullable = false)
    private String name ;

    @NotNull
    @Column(nullable = false)
    private Double capacityWeight;

    @NotNull
    @Column(nullable = false)
    private Double capacityVolume;

    @NotNull
    @Column(nullable = false)
    private Integer maxDeliveries;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}

package org.example.vehicle.model;

import lombok.*;
import org.example.vehicle.entity.VehicleType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VehiclesModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Vehicle {
        private UUID id;
        private String brand;
    }

    @Singular
    private List<Vehicle> vehicles;
}

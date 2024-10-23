package org.example.vehicle.dto;

import lombok.*;
import org.example.vehicle.entity.VehicleType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class GetVehiclesResponse {
    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    public static class Vehicle {
        private UUID uuid;
        private String model;
        private String brand;
        private VehicleType type;
    }

    @Singular
    private List<Vehicle> vehicles;
}

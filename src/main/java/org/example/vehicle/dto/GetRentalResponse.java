package org.example.vehicle.dto;

import lombok.*;
import org.example.vehicle.entity.VehicleType;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class GetRentalResponse {
    private UUID id;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean returned;

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    public static class Vehicle {
        private UUID id;
        private String model;
        private String brand;
        private VehicleType type;
    }

    private Vehicle vehicle;
}

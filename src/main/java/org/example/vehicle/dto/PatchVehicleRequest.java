package org.example.vehicle.dto;

import lombok.*;
import org.example.vehicle.entity.VehicleType;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class PatchVehicleRequest {
    private String model;
    private String brand;
    private VehicleType type;
}

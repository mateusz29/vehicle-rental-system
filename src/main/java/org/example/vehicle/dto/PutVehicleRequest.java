package org.example.vehicle.dto;

import lombok.*;
import org.example.vehicle.entity.VehicleType;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class PutVehicleRequest {
    private UUID uuid;
    private String model;
    private String brand;
    private VehicleType type;
}

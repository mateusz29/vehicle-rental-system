package org.example.vehicle.model;

import lombok.*;
import org.example.vehicle.entity.VehicleType;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VehicleModel {
    private UUID id;
    private String model;
    private String brand;
    private VehicleType type;
}

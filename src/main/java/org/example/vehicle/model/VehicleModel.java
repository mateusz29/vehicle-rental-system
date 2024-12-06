package org.example.vehicle.model;

import lombok.*;
import org.example.vehicle.entity.VehicleType;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VehicleModel implements Serializable {
    private UUID id;
    private String model;
    private String brand;
    private VehicleType type;
}

package org.example.vehicle.model;

import lombok.*;
import org.example.vehicle.entity.VehicleType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VehicleEditModel {
    private String model;
    private String brand;
    private VehicleType type;
}

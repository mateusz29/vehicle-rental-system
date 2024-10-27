package org.example.vehicle.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Vehicle implements Serializable{
    private UUID id;
    private String model;
    private String brand;
    private VehicleType type;
}
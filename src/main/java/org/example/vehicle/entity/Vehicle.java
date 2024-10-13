package org.example.vehicle.entity;

import lombok.*;
import org.example.rental.entity.Rental;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Vehicle implements Serializable{
    private UUID uuid;
    private String model;
    private String brand;
    private VehicleType type;

    @Singular
    private List<Rental> rentals;
}
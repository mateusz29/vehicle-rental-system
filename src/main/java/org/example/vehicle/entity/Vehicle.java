package org.example.vehicle.entity;

import jakarta.persistence.*;
import lombok.*;

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
@Entity
@Table(name = "vehicles")
public class Vehicle implements Serializable{
    @Id
    private UUID id;
    private String model;
    private String brand;
    private VehicleType type;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Rental> rentals;
}
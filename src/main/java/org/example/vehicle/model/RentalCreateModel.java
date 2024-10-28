package org.example.vehicle.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RentalCreateModel {
    private UUID id;
    private String referenceCode;
    private String rentalDate;
    private String returnDate;
    private boolean returned;
    private VehicleModel vehicle;
}

package org.example.vehicle.model;

import lombok.*;

import java.time.LocalDate;
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
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean returned;
    private VehicleModel vehicle;
}

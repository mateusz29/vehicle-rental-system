package org.example.vehicle.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RentalEditModel {
    private String referenceCode;
    private String rentalDate;
    private String returnDate;
    private boolean returned;
    private VehicleModel vehicle;
}

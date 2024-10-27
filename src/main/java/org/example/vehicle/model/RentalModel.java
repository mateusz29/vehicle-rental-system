package org.example.vehicle.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RentalModel {
    private String referenceCode;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean returned;
    private String vehicle;
}

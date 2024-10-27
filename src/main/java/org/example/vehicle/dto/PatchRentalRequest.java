package org.example.vehicle.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class PatchRentalRequest {
    private String referenceCode;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean returned;
}

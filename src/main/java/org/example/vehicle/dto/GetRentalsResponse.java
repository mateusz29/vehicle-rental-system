package org.example.vehicle.dto;

import lombok.*;

import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class GetRentalsResponse {
    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    public static class Rental {
        private UUID id;
        private String referenceCode;
        private LocalDate rentalDate;
        private LocalDate returnDate;
        private boolean returned;
    }

    @Singular
    private List<Rental> rentals;
}

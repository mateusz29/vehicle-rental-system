package org.example.vehicle.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class PutRentalRequest {
    private UUID uuid;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean returned;

    private UUID user;
    private UUID vehicle;
}

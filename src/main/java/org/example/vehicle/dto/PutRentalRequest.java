package org.example.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.vehicle.domain.StatsHolder;
import org.example.vehicle.validation.binding.ValidRentalStats;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@ValidRentalStats
public class PutRentalRequest implements StatsHolder {
    @NotBlank
    private String referenceCode;

    @NotNull
    private LocalDate rentalDate;

    @NotNull
    private LocalDate returnDate;

    @NotNull
    private boolean returned;

    @NotNull
    private Long version;

    @NotNull
    private UUID user;

    @NotNull
    private UUID vehicle;
}

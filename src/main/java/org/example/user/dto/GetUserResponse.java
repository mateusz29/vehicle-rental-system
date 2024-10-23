package org.example.user.dto;

import lombok.*;
import org.example.user.entity.User;
import org.example.vehicle.entity.Vehicle;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {
    private UUID uuid;
    private String username;
    private LocalDate birthday;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Rental {
        private UUID uuid;
        private LocalDate rentalDate;
        private LocalDate returnDate;
        private boolean returned;
    }

    @Singular
    private List<Rental> rentals;
}

package org.example.vehicle.entity;

import lombok.*;
import org.example.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Rental implements Serializable {
    private UUID uuid;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean returned;
    private User user;
    private Vehicle vehicle;
}

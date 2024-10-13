package org.example.user.entity;

import lombok.*;
import org.example.rental.entity.Rental;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class User implements Serializable {
    private UUID uuid;
    private String username;
    private LocalDate birthday;
    @Singular
    private List<Rental> rentals;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;
}

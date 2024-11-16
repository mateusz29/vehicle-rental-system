package org.example.vehicle.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Entity
@Table(name = "rentals")
public class Rental implements Serializable {
    @Id
    private UUID id;
    private String referenceCode;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean returned;

    @ManyToOne
    @JoinColumn(name = "user_username")
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;
}

package org.example.vehicle.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.entity.VersionAndCreationDateAuditable;
import org.example.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rentals")
public class Rental extends VersionAndCreationDateAuditable implements Serializable {
    @Id
    private UUID id;
    private String referenceCode;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean returned;

    @ManyToOne
    @JoinColumn(name = "user_username")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;

    @PrePersist
    @Override
    public void updateCreationDateTime() {
        super.updateCreationDateTime();
    }

}

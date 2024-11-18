package org.example.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.vehicle.entity.Rental;

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
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    private UUID id;
    private String username;
    @Column(name = "birth_date")
    private LocalDate birthday;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Rental> rentals;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;

    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "ud"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}

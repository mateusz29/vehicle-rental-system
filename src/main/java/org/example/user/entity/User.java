package org.example.user.entity;

import lombok.*;

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
public class User implements Serializable {
    private UUID uuid;
    private String username;
    private LocalDate birthday;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;
}

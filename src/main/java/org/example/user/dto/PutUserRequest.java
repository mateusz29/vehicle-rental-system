package org.example.user.dto;

import lombok.*;
import org.example.user.entity.UserRoles;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutUserRequest {
    private String username;
    private LocalDate birthday;
    private String password;
    private List<String> roles;
}

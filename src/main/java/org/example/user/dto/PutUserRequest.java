package org.example.user.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PutUserRequest {
    private UUID uuid;
    private String username;
    private LocalDate birthday;
}

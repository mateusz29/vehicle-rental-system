package org.example.user.controller.api;

import org.example.user.dto.GetUserResponse;
import org.example.user.dto.GetUsersResponse;
import org.example.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {
    GetUserResponse getUser(UUID uuid);
    GetUsersResponse getUsers();
    void deleteUser(UUID uuid);
    void updateOrCreateUser(PutUserRequest request);
    byte[] getAvatar(UUID uuid);
    void putAvatar(UUID uuid, InputStream avatar);
    void deleteAvatar(UUID uuid);
}

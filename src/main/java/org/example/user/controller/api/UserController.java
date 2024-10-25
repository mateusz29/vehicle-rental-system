package org.example.user.controller.api;

import org.example.user.dto.GetUserResponse;
import org.example.user.dto.GetUsersResponse;
import org.example.user.dto.PatchUserRequest;
import org.example.user.dto.PutUserRequest;

import java.util.UUID;

public interface UserController {
    GetUserResponse getUser(UUID uuid);
    GetUsersResponse getUsers();
    void deleteUser(UUID uuid);
    void updateUser(UUID uuid, PatchUserRequest request);
    void putUser(UUID uuid, PutUserRequest request);
}

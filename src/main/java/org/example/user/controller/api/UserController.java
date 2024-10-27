package org.example.user.controller.api;

import org.example.user.dto.GetUserResponse;
import org.example.user.dto.GetUsersResponse;
import org.example.user.dto.PatchUserRequest;
import org.example.user.dto.PutUserRequest;

import java.util.UUID;

public interface UserController {
    GetUserResponse getUser(UUID id);
    GetUsersResponse getUsers();
    void deleteUser(UUID id);
    void updateUser(UUID id, PatchUserRequest request);
    void putUser(UUID id, PutUserRequest request);
}

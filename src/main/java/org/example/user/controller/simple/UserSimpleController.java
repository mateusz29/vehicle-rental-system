package org.example.user.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.example.component.DtoFunctionFactory;
import org.example.user.controller.api.UserController;
import org.example.user.dto.GetUserResponse;
import org.example.user.dto.GetUsersResponse;
import org.example.user.dto.PatchUserRequest;
import org.example.user.dto.PutUserRequest;
import org.example.user.service.UserService;

import java.util.UUID;

@RequestScoped
public class UserSimpleController implements UserController {
    private final UserService userService;
    private final DtoFunctionFactory factory;

    @Inject
    public UserSimpleController(UserService userService, DtoFunctionFactory factory) {
        this.userService = userService;
        this.factory = factory;
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return userService.find(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(userService.findAll());
    }

    @Override
    public void deleteUser(UUID id) {
        userService.find(id).ifPresentOrElse(userService::delete, () -> {
                throw new NotFoundException();
            });
    }

    @Override
    public void updateUser(UUID id, PatchUserRequest request) {
        userService.find(id).ifPresentOrElse(
                user -> userService.update(factory.updateUser().apply(user, request)), () -> {
                throw new NotFoundException();
            }
        );
    }

    @Override
    public void putUser(UUID id, PutUserRequest request) {
        try {
            userService.create(factory.requestToUser().apply(id, request));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }
}

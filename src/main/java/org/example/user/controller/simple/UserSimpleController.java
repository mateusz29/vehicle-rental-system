package org.example.user.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.controller.servlet.exception.BadRequestException;
import org.example.controller.servlet.exception.NotFoundException;
import org.example.component.DtoFunctionFactory;
import org.example.user.controller.api.UserController;
import org.example.user.dto.GetUserResponse;
import org.example.user.dto.GetUsersResponse;
import org.example.user.dto.PatchUserRequest;
import org.example.user.dto.PutUserRequest;
import org.example.user.entity.User;
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
    public GetUserResponse getUser(UUID uuid) {
        return userService.find(uuid)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(userService.findAll());
    }

    @Override
    public void deleteUser(UUID uuid) {
        userService.find(uuid).ifPresentOrElse(userService::delete, () -> {
                throw new NotFoundException();
            });
    }

    @Override
    public void updateUser(UUID uuid, PatchUserRequest request) {
        userService.find(uuid).ifPresentOrElse(
                user -> userService.update(factory.updateUser().apply(user, request)), () -> {
                throw new NotFoundException();
            }
        );
    }

    @Override
    public void putUser(UUID uuid, PutUserRequest request) {
        try {
            userService.create(factory.requestToUser().apply(uuid, request));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }
}

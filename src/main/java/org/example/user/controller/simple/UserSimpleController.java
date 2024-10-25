package org.example.user.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.controller.servlet.exception.NotFoundException;
import org.example.component.DtoFunctionFactory;
import org.example.user.controller.api.UserController;
import org.example.user.dto.GetUserResponse;
import org.example.user.dto.GetUsersResponse;
import org.example.user.dto.PutUserRequest;
import org.example.user.entity.User;
import org.example.user.service.UserService;

import java.io.InputStream;
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
                .map(factory.userToResponeFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponseFunction().apply(userService.findAll());
    }

    @Override
    public void deleteUser(UUID uuid) {
        userService.find(uuid).ifPresentOrElse(userService::delete, () -> {
                throw new NotFoundException();
            });
    }

    @Override
    public void putUser(UUID uuid, PutUserRequest request) {
        User user = factory.requestToUserFunction().apply(uuid, request);

        if (userService.find(user.getUuid()).isPresent()) {
            userService.update(user);
        } else {
            userService.create(user);
        }
    }
}

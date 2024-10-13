package org.example.user.controller.simple;

import org.example.controller.servlet.exception.NotFoundException;
import org.example.component.DtoFunctionFactory;
import org.example.user.controller.api.UserController;
import org.example.user.dto.GetUserResponse;
import org.example.user.dto.GetUsersResponse;
import org.example.user.dto.PutUserRequest;
import org.example.user.entity.User;
import org.example.user.service.UserService;

import java.util.UUID;

public class UserSimpleController implements UserController {
    private final UserService userService;
    private final DtoFunctionFactory factory;

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
        return factory.usersToResponseFunction().apply(userService.findAllUsers());
    }

    @Override
    public void deleteUser(UUID uuid) {
        userService.find(uuid)
            .ifPresentOrElse(userService::deleteUser, () -> {
                throw new NotFoundException();
            });
    }

    @Override
    public void updateOrCreateUser(PutUserRequest request) {
        User user = factory.requestToUserFunction().apply(request);

        if (userService.find(user.getUuid()).isPresent()) {
            userService.updateUser(user);
        } else {
            userService.createUser(user);
        }
    }
}

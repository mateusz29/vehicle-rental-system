package org.example.user.controller.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Context;
import lombok.SneakyThrows;
import org.example.component.DtoFunctionFactory;
import org.example.user.controller.api.UserController;
import org.example.user.dto.GetUserResponse;
import org.example.user.dto.GetUsersResponse;
import org.example.user.dto.PatchUserRequest;
import org.example.user.dto.PutUserRequest;
import org.example.user.service.UserService;

import java.util.UUID;

@Path("")
public class UserRestController implements UserController {
    private final UserService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public UserRestController(
            UserService service,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return service.find(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @SneakyThrows
    public void putUser(UUID id, PutUserRequest request) {
        try {
            service.create(factory.requestToUser().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UserController.class, "getUser")
                    .build(id)
                    .toString());
            throw  new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void updateUser(UUID id, PatchUserRequest request) {
        service.find(id).ifPresentOrElse(
                user -> service.update(factory.updateUser().apply(user, request)), () -> {
                throw new NotFoundException();
            }
        );
    }

    @Override
    public void deleteUser(UUID id) {
        service.find(id).ifPresentOrElse(service::delete, () -> {
            throw new NotFoundException();
        });
    }

}

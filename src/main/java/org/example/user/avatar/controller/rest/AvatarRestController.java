package org.example.user.avatar.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import org.example.user.avatar.controller.api.AvatarController;
import org.example.user.avatar.service.AvatarService;
import org.example.user.service.UserService;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public class AvatarRestController implements AvatarController {
    private final AvatarService avatarService;
    private final UserService userService;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public AvatarRestController(AvatarService avatarService,
                                UserService userService,
                                @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.avatarService = avatarService;
        this.userService = userService;
        this.uriInfo = uriInfo;
    }

    @Override
    public byte[] getAvatar(UUID id) {
        return avatarService.get(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @SneakyThrows
    public void putAvatar(UUID id, InputStream is) {
        userService.find(id).ifPresentOrElse(
                entity -> {
                    avatarService.update(id, is);
                    response.setHeader("Location", uriInfo.getBaseUriBuilder()
                            .path(AvatarController.class, "getAvatar")
                            .build(id)
                            .toString());
                },
                () -> { throw new NotFoundException(); }
        );
        throw new WebApplicationException(Response.Status.CREATED);
    }

    @Override
    public void deleteAvatar(UUID id) {
        if (avatarService.get(id).isPresent()) {
            avatarService.delete(id);
        } else {
            throw new NotFoundException();
        }
    }
}

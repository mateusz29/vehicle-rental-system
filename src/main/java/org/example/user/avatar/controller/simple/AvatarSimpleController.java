package org.example.user.avatar.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.controller.servlet.exception.NotFoundException;
import org.example.user.avatar.controller.api.AvatarController;
import org.example.user.avatar.service.AvatarService;
import org.example.user.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequestScoped
public class AvatarSimpleController implements AvatarController {
    private final AvatarService avatarService;
    private final UserService userService;

    @Inject
    public AvatarSimpleController(AvatarService avatarService, UserService userService) {
        this.avatarService = avatarService;
        this.userService = userService;
    }

    @Override
    public byte[] getAvatar(UUID uuid) {
        return avatarService.get(uuid)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putAvatar(UUID uuid, InputStream is) {
        userService.find(uuid).ifPresentOrElse(entity -> avatarService.update(uuid, is), () -> {
            throw new NotFoundException();
        });
    }

    @Override
    public void deleteAvatar(UUID uuid) {
        if (avatarService.get(uuid).isPresent()) {
            avatarService.delete(uuid);
        } else {
            throw new NotFoundException();
        }
    }
}

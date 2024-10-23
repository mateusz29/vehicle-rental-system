package org.example.user.avatar.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.user.avatar.repository.api.AvatarRepository;
import org.example.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;

    @Inject
    public AvatarService(AvatarRepository avatarRepository, UserRepository userRepository) {
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
    }

    public void update(UUID uuid, InputStream is) {
        userRepository.find(uuid).ifPresent(entity -> {
            try {
                avatarRepository.updateAvatar(uuid, is.readAllBytes());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public Optional<byte[]> get(UUID uuid) {
        return avatarRepository.getAvatar(uuid);
    }

    public void delete(UUID uuid) {
        avatarRepository.deleteAvatar(uuid);
    }
}

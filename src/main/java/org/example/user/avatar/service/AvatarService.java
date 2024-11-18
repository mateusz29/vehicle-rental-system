package org.example.user.avatar.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.user.avatar.repository.api.AvatarRepository;
import org.example.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;

    @Inject
    public AvatarService(AvatarRepository avatarRepository, UserRepository userRepository) {
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
    }

    public void update(UUID id, InputStream is) {
        userRepository.find(id).ifPresent(entity -> {
            try {
                avatarRepository.updateAvatar(id, is.readAllBytes());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public Optional<byte[]> get(UUID id) {
        return avatarRepository.getAvatar(id);
    }

    public void delete(UUID id) {
        avatarRepository.deleteAvatar(id);
    }
}

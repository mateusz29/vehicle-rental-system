package org.example.user.avatar.repository.api;

import java.util.Optional;
import java.util.UUID;

public interface AvatarRepository {
    Optional<byte[]> getAvatar(UUID uuid);
    void updateAvatar(UUID uuid, byte[] avatar);
    void deleteAvatar(UUID uuid);
}

package org.example.user.avatar.repository.api;

import java.util.Optional;
import java.util.UUID;

public interface AvatarRepository {
    Optional<byte[]> getAvatar(UUID id);
    void updateAvatar(UUID id, byte[] avatar);
    void deleteAvatar(UUID id);
}

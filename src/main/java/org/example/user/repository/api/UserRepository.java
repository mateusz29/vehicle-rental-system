package org.example.user.repository.api;

import org.example.repository.api.Repository;
import org.example.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {
    Optional<User> findByUsername(String username);
    byte[] getAvatar(UUID uuid);
    void updateAvatar(UUID uuid, byte[] avatar);
    void deleteAvatar(UUID uuid);
    void createAvatar(UUID uuid, byte[] avatar);
}

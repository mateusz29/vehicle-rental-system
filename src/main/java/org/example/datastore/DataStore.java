package org.example.datastore;

import org.example.controller.servlet.exception.NotFoundException;
import org.example.serialization.CloningUtility;
import org.example.user.entity.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.UUID;

public class DataStore {
    private final Set<User> users = new HashSet<>();
    private final CloningUtility cloningUtility;
    private final Path avatarDirectory;

    public DataStore(CloningUtility cloningUtility, Path avatarDirectory) {
        this.cloningUtility = cloningUtility;
        this.avatarDirectory = avatarDirectory;
    }

    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getUuid().equals(value.getUuid()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getUuid()));
        }
        users.add(cloningUtility.clone(value));
    }

    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(user -> user.getUuid().equals(value.getUuid()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getUuid()));
        }
    }

    public synchronized void deleteUser(UUID uuid) {
        if (!users.removeIf(user -> user.getUuid().equals(uuid))) {
            throw new IllegalArgumentException("There is no user with \"%s\"".formatted(uuid));
        }
    }

    public Path getAvatarPath(UUID userUUID) {
        return avatarDirectory.resolve(userUUID.toString() + ".png");
    }

    public Optional<byte[]> getAvatar(UUID uuid) {
        Path avatarPath = getAvatarPath(uuid);
        try {
            if (Files.exists(avatarPath)) {
                return Optional.of(Files.readAllBytes(avatarPath));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new RuntimeException("Avatar for user with id \"%s\" does not exist".formatted(uuid));
        }
    }

    public void updateAvatar(UUID uuid, byte[] avatar) {
        Path avatarPath = getAvatarPath(uuid);
        try {
            Files.write(avatarPath, avatar);
        } catch (IOException e) {
            throw new RuntimeException("Cannot update avatar for user with id \"%s\"".formatted(uuid));
        }
    }

    public void deleteAvatar(UUID uuid) {
        Path avatarPath = getAvatarPath(uuid);
        try {
            if (!Files.exists(avatarPath)) {
                throw new IllegalArgumentException("Avatar for user with id \"%s\" does not exist".formatted(uuid));
            }
            Files.delete(avatarPath);
        } catch (IOException e) {
            throw new RuntimeException("Cannot delete avatar for user with id \"%s\"".formatted(uuid));
        }
    }
}

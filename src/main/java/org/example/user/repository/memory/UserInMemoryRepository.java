package org.example.user.repository.memory;

import org.example.datastore.DataStore;
import org.example.user.entity.User;
import org.example.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserInMemoryRepository implements UserRepository {
    private final DataStore store;

    public UserInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<User> find(UUID uuid) {
        return store.findAllUsers().stream()
                .filter(user -> user.getUuid().equals(uuid))
                .findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return store.findAllUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        store.deleteUser(entity.getUuid());
    }

    @Override
    public void update(User entity) {
        store.updateUser(entity);
    }

    @Override
    public List<User> findAll() {
        return store.findAllUsers();
    }

    public Optional<byte[]> getAvatar(UUID uuid) {
        return store.getAvatar(uuid);
    }

    public void updateAvatar(UUID uuid, byte[] avatar) {
        store.updateAvatar(uuid, avatar);
    }

    public void deleteAvatar(UUID uuid) {
        store.deleteAvatar(uuid);
    }
}

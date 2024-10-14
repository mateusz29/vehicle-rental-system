package org.example.user.service;

import org.example.user.entity.User;
import org.example.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> find(UUID uuid) {
        return repository.find(uuid);
    }

    public Optional<User> find(String username) {
        return repository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public void createUser(User user) {
        repository.create(user);
    }

    public void deleteUser(User user) {
        repository.delete(user);
    }

    public void updateUser(User user) {
        repository.update(user);
    }

    public byte[] getAvatar(UUID uuid) {
        return repository.getAvatar(uuid);
    }

    public void updateAvatar(UUID uuid, InputStream avatar) throws IOException {
        repository.updateAvatar(uuid, avatar.readAllBytes());
    }

    public void deleteAvatar(UUID uuid) {
        repository.deleteAvatar(uuid);
    }

    public void createAvatar(UUID uuid, byte[] avatar) {
        repository.createAvatar(uuid, avatar);
    }
}
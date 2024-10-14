package org.example.user.service;

import org.example.controller.servlet.exception.NotFoundException;
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

    public Optional<byte[]> getAvatar(UUID uuid) {
        return repository.getAvatar(uuid);
    }

    public void updateAvatar(UUID uuid, InputStream avatar){
        repository.find(uuid).ifPresent(user -> {
            try {
                repository.updateAvatar(uuid, avatar.readAllBytes());
            } catch (IOException e) {
                throw new NotFoundException();
            }
        });
    }

    public void deleteAvatar(UUID uuid) {
        repository.deleteAvatar(uuid);
    }
}
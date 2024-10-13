package org.example.user.entity.service;


import org.example.user.entity.User;
import org.example.user.entity.repository.api.UserRepository;

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
}
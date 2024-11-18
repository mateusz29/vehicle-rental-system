package org.example.user.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.user.entity.User;
import org.example.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository repository;

    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    public Optional<User> find(String username) {
        return repository.findByUsername(username);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void create(User user) {
        repository.create(user);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    public void update(User user) {
        repository.update(user);
    }
}
package org.example.user.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import org.example.user.entity.User;
import org.example.user.entity.UserRoles;
import org.example.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository repository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public UserService(
            UserRepository repository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<User> find(String username) {
        return repository.findByUsername(username);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public List<User> findAll() {
        return repository.findAll();
    }

    @PermitAll
    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void update(User user) {
        repository.update(user);
    }

    @PermitAll
    public boolean verify(String username, String password) {
        return find(username)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }
}
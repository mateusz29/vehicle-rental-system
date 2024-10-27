package org.example.user.repository.memory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.datastore.component.DataStore;
import org.example.user.entity.User;
import org.example.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserInMemoryRepository implements UserRepository {
    private final DataStore store;

    @Inject
    public UserInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<User> find(UUID id) {
        return store.findAllUsers().stream()
                .filter(user -> user.getId().equals(id))
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
        store.deleteUser(entity.getId());
    }

    @Override
    public void update(User entity) {
        store.updateUser(entity);
    }

    @Override
    public List<User> findAll() {
        return store.findAllUsers();
    }
}

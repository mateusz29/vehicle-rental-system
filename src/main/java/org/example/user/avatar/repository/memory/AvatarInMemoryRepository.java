package org.example.user.avatar.repository.memory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.datastore.component.DataStore;
import org.example.user.avatar.repository.api.AvatarRepository;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AvatarInMemoryRepository implements AvatarRepository {
    private final DataStore store;

    @Inject
    public AvatarInMemoryRepository(DataStore store) {
        this.store = store;
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
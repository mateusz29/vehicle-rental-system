package org.example.user.avatar.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.user.avatar.repository.api.AvatarRepository;

import java.util.Optional;
import java.util.UUID;

@Dependent
public class AvatarPersistenceRepository implements AvatarRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<byte[]> getAvatar(UUID id) {
        return Optional.empty();
    }

    @Override
    public void updateAvatar(UUID id, byte[] avatar) {

    }

    @Override
    public void deleteAvatar(UUID id) {

    }

}

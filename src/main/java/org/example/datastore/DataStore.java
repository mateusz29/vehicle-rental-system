package org.example.datastore;

import org.example.serialization.CloningUtility;
import org.example.user.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataStore {
    private final Set<User> users = new HashSet<>();
    private final CloningUtility cloningUtility;

    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
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
}

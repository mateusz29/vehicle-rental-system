package org.example.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import lombok.NoArgsConstructor;
import org.example.serialization.component.CloningUtility;
import org.example.user.entity.User;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {
    private final Set<User> users = new HashSet<>();
    private final Set<Vehicle> vehicles = new HashSet<>();
    private final Set<Rental> rentals = new HashSet<>();

    private final CloningUtility cloningUtility;
    private final Path avatarDirectory;

    @Inject
    public DataStore(CloningUtility cloningUtility, ServletContext servletContext) {
        this.cloningUtility = cloningUtility;
        this.avatarDirectory = Paths.get(servletContext.getInitParameter("avatarDirectory"));
    }

    public synchronized List<Vehicle> findAllVehicles() {
        return vehicles.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createVehicle(Vehicle value) throws IllegalArgumentException {
        if (vehicles.stream().anyMatch(vehicle -> vehicle.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The vehicle id \"%s\" is not unique".formatted(value.getId()));
        }
        vehicles.add(cloningUtility.clone(value));
    }

    public synchronized void updateVehicle(Vehicle value) throws IllegalArgumentException {
        Vehicle entity = cloningUtility.clone(value);
        if (vehicles.removeIf(vehicle -> vehicle.getId().equals(value.getId()))) {
            vehicles.add(entity);
        } else {
            throw new IllegalArgumentException("The vehicle with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteVehicle(UUID id) {
        if (!vehicles.removeIf(vehicle -> vehicle.getId().equals(id))) {
            throw new IllegalArgumentException("The vehicle with id \"%s\" does not exist".formatted(id));
        } else {
            rentals.removeIf(rental -> rental.getVehicle().getId().equals(id));
        }
    }

    public synchronized List<Rental> findAllRentals() {
        return rentals.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createRental(Rental value) throws IllegalArgumentException{
        if (rentals.stream().anyMatch(rental -> rental.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The rental id \"%s\" is not unique".formatted(value.getId()));
        }
        rentals.add(cloneWithRelationships(value));
    }

    public synchronized void updateRental(Rental value)throws IllegalArgumentException {
        if (rentals.removeIf(rental -> rental.getId().equals(value.getId()))) {
            rentals.add(cloneWithRelationships(value));
        } else {
            throw new IllegalArgumentException("The rental with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteRental(UUID id) {
        if (!rentals.removeIf(rental -> rental.getId().equals(id))) {
            throw new IllegalArgumentException("The rental with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(user -> user.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteUser(UUID id) {
        if (!users.removeIf(user -> user.getId().equals(id))) {
            throw new IllegalArgumentException("There is no user with \"%s\"".formatted(id));
        }
    }

    public synchronized Path getAvatarPath(UUID userUUID) {
        return avatarDirectory.resolve(userUUID.toString() + ".png");
    }

    public synchronized Optional<byte[]> getAvatar(UUID id) {
        Path avatarPath = getAvatarPath(id);
        try {
            if (Files.exists(avatarPath)) {
                return Optional.of(Files.readAllBytes(avatarPath));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new RuntimeException("Avatar for user with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized void updateAvatar(UUID id, byte[] avatar) {
        Path avatarPath = getAvatarPath(id);
        try {
            Files.write(avatarPath, avatar);
        } catch (IOException e) {
            throw new RuntimeException("Cannot update avatar for user with id \"%s\"".formatted(id));
        }
    }

    public synchronized void deleteAvatar(UUID id) {
        Path avatarPath = getAvatarPath(id);
        try {
            if (!Files.exists(avatarPath)) {
                throw new IllegalArgumentException("Avatar for user with id \"%s\" does not exist".formatted(id));
            }
            Files.delete(avatarPath);
        } catch (IOException e) {
            throw new RuntimeException("Cannot delete avatar for user with id \"%s\"".formatted(id));
        }
    }

    private Rental cloneWithRelationships(Rental value) {
        Rental entity = cloningUtility.clone(value);

        if (entity.getUser() != null) {
            entity.setUser(users.stream()
                    .filter(user -> user.getId().equals(value.getUser().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
        }

        if (entity.getVehicle() != null) {
            entity.setVehicle(vehicles.stream()
                    .filter(vehicle -> vehicle.getId().equals(value.getVehicle().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No vehicle with id \"%s\".".formatted(value.getVehicle().getId()))));
        }

        return entity;
    }
}

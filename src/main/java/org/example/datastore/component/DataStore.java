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

    public synchronized List<Rental> findAllRentals() {
        return rentals.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createRental(Rental value) {
        if (rentals.stream().anyMatch(rental -> rental.getUuid().equals(value.getUuid()))) {
            throw new IllegalArgumentException("The rental id \"%s\" is not unique".formatted(value.getUuid()));
        }
        Rental entity = cloneWithRelationships(value);
        rentals.add(entity);
    }

    public synchronized void updateRental(Rental value) {
        if (rentals.removeIf(rental -> rental.getUuid().equals(value.getUuid()))) {
            Rental entity = cloneWithRelationships(value);
            rentals.add(entity);
        } else {
            throw new IllegalArgumentException("The rental with id \"%s\" does not exist".formatted(value.getUuid()));
        }
    }

    public synchronized void deleteRental(UUID uuid) {
        if (!rentals.removeIf(rental -> rental.getUuid().equals(uuid))) {
            throw new IllegalArgumentException("The rental with id \"%s\" does not exist".formatted(uuid));
        }
    }

    public synchronized List<Vehicle> findAllVehicles() {
//        return vehicles.stream()
//                .map(cloningUtility::clone)
//                .collect(Collectors.toList());
        return vehicles.stream()
                .map(vehicle -> {
                    Vehicle clonedVehicle = cloningUtility.clone(vehicle);
                    List<Rental> vehicleRentals = rentals.stream()
                            .filter(rental -> rental.getVehicle().getUuid().equals(clonedVehicle.getUuid()))
                            .map(cloningUtility::clone)
                            .collect(Collectors.toList());
                    clonedVehicle.setRentals(vehicleRentals);
                    return clonedVehicle;
                })
                .collect(Collectors.toList());
    }

    public synchronized void createVehicle(Vehicle value) throws IllegalArgumentException {
        if (vehicles.stream().anyMatch(vehicle -> vehicle.getUuid().equals(value.getUuid()))) {
            throw new IllegalArgumentException("The vehicle id \"%s\" is not unique".formatted(value.getUuid()));
        }
        vehicles.add(cloningUtility.clone(value));
    }

    public synchronized void updateVehicle(Vehicle value) throws IllegalArgumentException {
        if (vehicles.removeIf(vehicle -> vehicle.getUuid().equals(value.getUuid()))) {
            vehicles.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The vehicle with id \"%s\" does not exist".formatted(value.getUuid()));
        }
    }

    public synchronized void deleteVehicle(Vehicle value) {
        if (!vehicles.remove(value)) {
            throw new IllegalArgumentException("The vehicle with id \"%s\" does not exist".formatted(value.getUuid()));
        }
    }

    public synchronized List<User> findAllUsers() {
//        return users.stream()
//                .map(cloningUtility::clone)
//                .collect(Collectors.toList());
        return users.stream()
                .map(user -> {
                    User clonedUser = cloningUtility.clone(user);
                    List<Rental> userRentals = rentals.stream()
                            .filter(rental -> rental.getUser().getUuid().equals(clonedUser.getUuid()))
                            .map(cloningUtility::clone)
                            .collect(Collectors.toList());
                    clonedUser.setRentals(userRentals);
                    return clonedUser;
                })
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

    public synchronized void deleteUser(UUID uuid) {
        if (!users.removeIf(user -> user.getUuid().equals(uuid))) {
            throw new IllegalArgumentException("There is no user with \"%s\"".formatted(uuid));
        }
    }

    public Path getAvatarPath(UUID userUUID) {
        return avatarDirectory.resolve(userUUID.toString() + ".png");
    }

    public Optional<byte[]> getAvatar(UUID uuid) {
        Path avatarPath = getAvatarPath(uuid);
        try {
            if (Files.exists(avatarPath)) {
                return Optional.of(Files.readAllBytes(avatarPath));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new RuntimeException("Avatar for user with id \"%s\" does not exist".formatted(uuid));
        }
    }

    public void updateAvatar(UUID uuid, byte[] avatar) {
        Path avatarPath = getAvatarPath(uuid);
        try {
            Files.write(avatarPath, avatar);
        } catch (IOException e) {
            throw new RuntimeException("Cannot update avatar for user with id \"%s\"".formatted(uuid));
        }
    }

    public void deleteAvatar(UUID uuid) {
        Path avatarPath = getAvatarPath(uuid);
        try {
            if (!Files.exists(avatarPath)) {
                throw new IllegalArgumentException("Avatar for user with id \"%s\" does not exist".formatted(uuid));
            }
            Files.delete(avatarPath);
        } catch (IOException e) {
            throw new RuntimeException("Cannot delete avatar for user with id \"%s\"".formatted(uuid));
        }
    }

    private Rental cloneWithRelationships(Rental value) {
        Rental entity = cloningUtility.clone(value);

        if (entity.getUser() != null) {
            User user = users.stream()
                    .filter(u -> u.getUuid().equals(value.getUser().getUuid()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getUuid())));
            entity.setUser(user);
        }

        if (entity.getVehicle() != null) {
            Vehicle vehicle = vehicles.stream()
                    .filter(v -> v.getUuid().equals(value.getVehicle().getUuid()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No vehicle with id \"%s\".".formatted(value.getVehicle().getUuid())));
            entity.setVehicle(vehicle);
        }

        return entity;
    }
}

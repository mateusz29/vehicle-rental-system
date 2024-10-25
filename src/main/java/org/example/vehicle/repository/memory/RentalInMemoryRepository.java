package org.example.vehicle.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.datastore.component.DataStore;
import org.example.user.entity.User;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.repository.api.RentalRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class RentalInMemoryRepository implements RentalRepository {
    private final DataStore store;

    @Inject
    public RentalInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Rental> find(UUID uuid) {;
        return store.findAllRentals().stream()
                .filter(rental -> rental.getUuid().equals(uuid))
                .findFirst();
    }

    @Override
    public List<Rental> findAll() {
        return store.findAllRentals();
    }

    @Override
    public void create(Rental entity) {
        store.createRental(entity);
    }

    @Override
    public void delete(Rental entity) {
        store.deleteRental(entity.getUuid());
    }

    @Override
    public void update(Rental entity) {
        store.updateRental(entity);
    }

    @Override
    public List<Rental> findAllByUser(User user) {
        return store.findAllRentals().stream()
                .filter(rental -> user.equals(rental.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rental> findAllByVehicle(Vehicle vehicle) {
        return store.findAllRentals().stream()
                .filter(rental -> vehicle.equals(rental.getVehicle()))
                .collect(Collectors.toList());
    }
}

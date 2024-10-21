package org.example.vehicle.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.datastore.component.DataStore;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.repository.api.VehicleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class VehicleInMemoryRepository implements VehicleRepository {
    private final DataStore store;

    @Inject
    public VehicleInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Vehicle> find(UUID uuid) {
        return store.findAllVehicles().stream()
                .filter(vehicle -> vehicle.getUuid().equals(uuid))
                .findFirst();
    }

    @Override
    public List<Vehicle> findAll() {
        return store.findAllVehicles();
    }

    @Override
    public void create(Vehicle entity) {
        store.createVehicle(entity);
    }

    @Override
    public void delete(Vehicle entity) {
        store.deleteVehicle(entity);
    }

    @Override
    public void update(Vehicle entity) {
        store.updateVehicle(entity);
    }
}

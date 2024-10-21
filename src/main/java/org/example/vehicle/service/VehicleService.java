package org.example.vehicle.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.repository.api.RentalRepository;
import org.example.vehicle.repository.api.VehicleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class VehicleService {
    private final VehicleRepository repository;
    private final RentalRepository rentalRepository;

    @Inject
    public VehicleService(VehicleRepository repository, RentalRepository rentalRepository) {
        this.repository = repository;
        this.rentalRepository = rentalRepository;
    }

    public Optional<Vehicle> find(UUID uuid) {
        return repository.find(uuid);
    }

    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    public void create(Vehicle vehicle) {
        repository.create(vehicle);
    }

    public void update(Vehicle vehicle) {
        repository.update(vehicle);
    }

    public void delete(Vehicle vehicle) {
        repository.delete(vehicle);
    }
}

package org.example.vehicle.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

    @Inject
    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public Optional<Vehicle> find(UUID id) {
        return repository.find(id);
    }

    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(Vehicle vehicle) {
        repository.create(vehicle);
    }

    @Transactional
    public void update(Vehicle vehicle) {
        repository.update(vehicle);
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }
}

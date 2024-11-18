package org.example.vehicle.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.repository.api.VehicleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
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

    public void create(Vehicle vehicle) {
        repository.create(vehicle);
    }

    public void update(Vehicle vehicle) {
        repository.update(vehicle);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }
}

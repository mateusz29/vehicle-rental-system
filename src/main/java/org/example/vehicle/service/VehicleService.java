package org.example.vehicle.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.user.entity.UserRoles;
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

    @PermitAll
    public Optional<Vehicle> find(UUID id) {
        return repository.find(id);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(Vehicle vehicle) {
        repository.create(vehicle);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void update(Vehicle vehicle) {
        repository.update(vehicle);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }
}

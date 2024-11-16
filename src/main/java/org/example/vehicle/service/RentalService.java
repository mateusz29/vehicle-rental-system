package org.example.vehicle.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import org.example.user.repository.api.UserRepository;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.repository.api.RentalRepository;
import org.example.vehicle.repository.api.VehicleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class RentalService {
    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    @Inject
    public RentalService(VehicleRepository vehicleRepository, RentalRepository rentalRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    public Optional<Rental> find(UUID id) {
        return rentalRepository.find(id);
    }

    public Optional<Rental> findByVehicle(UUID vehicleId, UUID rentalId) {
        return findAllByVehicle(vehicleId)
                .flatMap(rentals -> rentals.stream()
                        .filter(rental -> rental.getId().equals(rentalId))
                        .findFirst());
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Transactional
    public void create(Rental rental, UUID vehicleId) {
        vehicleRepository.find(vehicleId).ifPresentOrElse(
                vehicle -> {
                    rental.setVehicle(vehicle);
                    rentalRepository.create(rental);
                },
                () -> {
                    throw new NotFoundException("The vehicle with id \"%s\" does not exist".formatted(vehicleId));
                }
        );
    }

    @Transactional
    public void update(Rental rental) {
        rentalRepository.update(rental);
    }

    @Transactional
    public void delete(UUID id) {
        rentalRepository.delete(rentalRepository.find(id).orElseThrow());
    }

    public Optional<List<Rental>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(rentalRepository::findAllByUser);
    }

    public Optional<List<Rental>> findAllByVehicle(UUID id) {
        return vehicleRepository.find(id)
                .map(rentalRepository::findAllByVehicle);
    }
}

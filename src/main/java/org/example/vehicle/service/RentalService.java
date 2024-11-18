package org.example.vehicle.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import org.example.user.repository.api.UserRepository;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.repository.api.RentalRepository;
import org.example.vehicle.repository.api.VehicleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
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

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public void create(Rental rental) {
        if (rentalRepository.find(rental.getId()).isPresent()) {
            throw new IllegalArgumentException("Rental with id \"%s\" already exists".formatted(rental.getId()));
        }
        if (vehicleRepository.find(rental.getVehicle().getId()).isEmpty()) {
            throw new NotFoundException("The vehicle with id \"%s\" does not exist".formatted(rental.getVehicle().getId()));
        }
        rentalRepository.create(rental);
    }

    public void update(Rental rental) {
        rentalRepository.update(rental);
    }

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

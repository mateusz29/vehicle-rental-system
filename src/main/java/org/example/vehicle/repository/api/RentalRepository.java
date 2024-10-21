package org.example.vehicle.repository.api;

import org.example.repository.api.Repository;
import org.example.user.entity.User;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;

import java.util.List;
import java.util.UUID;

public interface RentalRepository extends Repository<Rental, UUID> {
    List<Rental> findAllByUser(User user);
    List<Rental> findAllByVehicle(Vehicle vehicle);
}

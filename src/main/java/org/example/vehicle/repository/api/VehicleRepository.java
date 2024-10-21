package org.example.vehicle.repository.api;

import org.example.repository.api.Repository;
import org.example.vehicle.entity.Vehicle;

import java.util.UUID;

public interface VehicleRepository extends Repository<Vehicle, UUID> {
}

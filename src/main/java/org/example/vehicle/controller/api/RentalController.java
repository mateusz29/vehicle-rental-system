package org.example.vehicle.controller.api;

import org.example.vehicle.dto.GetRentalResponse;
import org.example.vehicle.dto.GetRentalsResponse;
import org.example.vehicle.dto.PutRentalRequest;

import java.util.UUID;

public interface RentalController {
    GetRentalResponse getRental(UUID uuid);
    GetRentalsResponse getRentals();
    GetRentalsResponse getVehicleRentals(UUID uuid);
    GetRentalsResponse getUserRentals(UUID uuid);
    void putRental(UUID uuid, PutRentalRequest request);
    void deleteRental(UUID uuid);
}

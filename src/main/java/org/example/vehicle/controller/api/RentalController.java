package org.example.vehicle.controller.api;

import org.example.vehicle.dto.GetRentalResponse;
import org.example.vehicle.dto.GetRentalsResponse;
import org.example.vehicle.dto.PatchRentalRequest;
import org.example.vehicle.dto.PutRentalRequest;

import java.util.UUID;

public interface RentalController {
    GetRentalResponse getRental(UUID id);
    GetRentalsResponse getRentals();
    GetRentalsResponse getVehicleRentals(UUID id);
    GetRentalsResponse getUserRentals(UUID id);
    void putRental(UUID id, PutRentalRequest request);
    void updateRental(UUID id, PatchRentalRequest request);
    void deleteRental(UUID id);
}

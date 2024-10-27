package org.example.vehicle.controller.api;

import org.example.vehicle.dto.GetVehicleResponse;
import org.example.vehicle.dto.GetVehiclesResponse;
import org.example.vehicle.dto.PatchVehicleRequest;
import org.example.vehicle.dto.PutVehicleRequest;

import java.util.UUID;

public interface VehicleController {
    GetVehicleResponse getVehicle(UUID id);
    GetVehiclesResponse getVehicles();
    void putVehicle(UUID id, PutVehicleRequest request);
    void updateVehicle(UUID id, PatchVehicleRequest request);
    void deleteVehicle(UUID id);
}

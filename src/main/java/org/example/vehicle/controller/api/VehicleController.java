package org.example.vehicle.controller.api;

import org.example.vehicle.dto.GetVehicleResponse;
import org.example.vehicle.dto.GetVehiclesResponse;
import org.example.vehicle.dto.PutVehicleRequest;

import java.util.UUID;

public interface VehicleController {
    GetVehicleResponse getVehicle(UUID uuid);
    GetVehiclesResponse getVehicles();
    void putVehicle(PutVehicleRequest request);
    void deleteVehicle(UUID uuid);
}

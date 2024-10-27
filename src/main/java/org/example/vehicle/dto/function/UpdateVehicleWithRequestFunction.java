package org.example.vehicle.dto.function;

import org.example.vehicle.dto.PatchVehicleRequest;
import org.example.vehicle.entity.Vehicle;

import java.util.function.BiFunction;

public class UpdateVehicleWithRequestFunction implements BiFunction<Vehicle, PatchVehicleRequest, Vehicle> {
    @Override
    public Vehicle apply(Vehicle vehicle, PatchVehicleRequest patchVehicleRequest) {
        return Vehicle.builder()
                .id(vehicle.getId())
                .model(patchVehicleRequest.getModel())
                .brand(patchVehicleRequest.getBrand())
                .type(patchVehicleRequest.getType())
                .build();
    }
}

package org.example.vehicle.dto.function;

import org.example.vehicle.dto.PutVehicleRequest;
import org.example.vehicle.entity.Vehicle;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToVehicleFunction implements BiFunction<UUID, PutVehicleRequest, Vehicle> {
    @Override
    public Vehicle apply(UUID uuid, PutVehicleRequest request) {
        return Vehicle.builder()
                .uuid(uuid)
                .model(request.getModel())
                .brand(request.getBrand())
                .type(request.getType())
                .build();
    }
}

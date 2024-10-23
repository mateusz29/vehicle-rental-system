package org.example.vehicle.dto.function;

import org.example.vehicle.dto.PutVehicleRequest;
import org.example.vehicle.entity.Vehicle;

import java.util.function.Function;

public class RequestToVehicleFunction implements Function<PutVehicleRequest, Vehicle> {
    @Override
    public Vehicle apply(PutVehicleRequest request) {
        return Vehicle.builder()
                .uuid(request.getUuid())
                .model(request.getModel())
                .brand(request.getBrand())
                .type(request.getType())
                .build();
    }
}

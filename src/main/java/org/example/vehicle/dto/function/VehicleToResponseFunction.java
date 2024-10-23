package org.example.vehicle.dto.function;

import org.example.vehicle.dto.GetVehicleResponse;
import org.example.vehicle.entity.Vehicle;

import java.util.function.Function;

public class VehicleToResponseFunction implements Function<Vehicle, GetVehicleResponse> {
    @Override
    public GetVehicleResponse apply(Vehicle vehicle) {
        return GetVehicleResponse.builder()
                .uuid(vehicle.getUuid())
                .model(vehicle.getModel())
                .brand(vehicle.getBrand())
                .type(vehicle.getType())
                .build();
    }
}

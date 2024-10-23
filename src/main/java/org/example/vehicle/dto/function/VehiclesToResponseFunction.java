package org.example.vehicle.dto.function;

import org.example.vehicle.dto.GetVehiclesResponse;
import org.example.vehicle.entity.Vehicle;

import java.util.List;
import java.util.function.Function;

public class VehiclesToResponseFunction implements Function<List<Vehicle>, GetVehiclesResponse> {
    @Override
    public GetVehiclesResponse apply(List<Vehicle> vehicles) {
        return GetVehiclesResponse.builder()
                .vehicles(vehicles.stream()
                        .map(vehicle -> GetVehiclesResponse.Vehicle.builder()
                                .uuid(vehicle.getUuid())
                                .model(vehicle.getModel())
                                .brand(vehicle.getBrand())
                                .type(vehicle.getType())
                                .build())
                        .toList())
                .build();
    }
}

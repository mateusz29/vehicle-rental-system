package org.example.vehicle.model.function;

import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.VehiclesModel;

import java.util.List;
import java.util.function.Function;

public class VehiclesToModelFunction implements Function<List<Vehicle>, VehiclesModel> {
    @Override
    public VehiclesModel apply(List<Vehicle> entity) {
        return VehiclesModel.builder()
                .vehicles(entity.stream()
                        .map(vehicle -> VehiclesModel.Vehicle.builder()
                                .id(vehicle.getId())
                                .brand(vehicle.getBrand())
                                .build())
                        .toList())
                .build();
    }
}

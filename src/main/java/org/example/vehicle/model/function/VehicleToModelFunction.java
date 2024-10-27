package org.example.vehicle.model.function;

import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.VehicleModel;

import java.io.Serializable;
import java.util.function.Function;

public class VehicleToModelFunction implements Function<Vehicle, VehicleModel>, Serializable {
    @Override
    public VehicleModel apply(Vehicle vehicle) {
        return VehicleModel.builder()
                .id(vehicle.getId())
                .model(vehicle.getModel())
                .brand(vehicle.getBrand())
                .type(vehicle.getType())
                .build();
    }
}

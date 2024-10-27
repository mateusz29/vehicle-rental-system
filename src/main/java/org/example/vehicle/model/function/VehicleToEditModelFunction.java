package org.example.vehicle.model.function;

import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.VehicleEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class VehicleToEditModelFunction implements Function<Vehicle, VehicleEditModel>, Serializable {
    @Override
    public VehicleEditModel apply(Vehicle vehicle) {
        return VehicleEditModel.builder()
                .model(vehicle.getModel())
                .brand(vehicle.getBrand())
                .type(vehicle.getType())
                .build();
    }
}

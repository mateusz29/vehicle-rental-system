package org.example.vehicle.model.function;

import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.VehicleEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateVehicleWithModelFunction implements BiFunction<Vehicle, VehicleEditModel, Vehicle>, Serializable {
    @Override
    public Vehicle apply(Vehicle vehicle, VehicleEditModel model) {
        return Vehicle.builder()
                .id(vehicle.getId())
                .model(model.getModel())
                .brand(model.getBrand())
                .type(model.getType())
                .build();
    }
}

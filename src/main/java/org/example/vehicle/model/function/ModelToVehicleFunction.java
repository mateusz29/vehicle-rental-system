package org.example.vehicle.model.function;

import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.VehicleCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToVehicleFunction implements Function<VehicleCreateModel, Vehicle>, Serializable {
    @Override
    public Vehicle apply(VehicleCreateModel model) {
        return Vehicle.builder()
                .id(model.getId())
                .model(model.getModel())
                .brand(model.getBrand())
                .type(model.getType())
                .build();
    }
}

package org.example.vehicle.model.function;

import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.RentalCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToRentalFunction implements Function<RentalCreateModel, Rental>, Serializable {
    @Override
    public Rental apply(RentalCreateModel model) {
        return Rental.builder()
                .id(model.getId())
                .rentalDate(model.getRentalDate())
                .returnDate(model.getReturnDate())
                .returned(model.isReturned())
                .vehicle(Vehicle.builder()
                        .id(model.getVehicle().getId())
                        .build())
                .build();
    }
}

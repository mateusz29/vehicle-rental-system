package org.example.vehicle.model.function;

import org.example.vehicle.entity.Rental;
import org.example.vehicle.model.RentalModel;

import java.io.Serializable;
import java.util.function.Function;

public class RentalToModelFunction implements Function<Rental, RentalModel>, Serializable {
    @Override
    public RentalModel apply(Rental entity) {
        return RentalModel.builder()
                .id(entity.getId())
                .rentalDate(entity.getRentalDate())
                .returnDate(entity.getReturnDate())
                .returned(entity.isReturned())
                .vehicle(entity.getVehicle().getBrand())
                .build();
    }
}

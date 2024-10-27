package org.example.vehicle.model.function;

import org.example.vehicle.entity.Rental;
import org.example.vehicle.model.RentalEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateRentalWithModelFunction implements BiFunction<Rental, RentalEditModel, Rental>, Serializable {
    @Override
    public Rental apply(Rental rental, RentalEditModel rentalEditModel) {
        return Rental.builder()
                .id(rental.getId())
                .rentalDate(rentalEditModel.getRentalDate())
                .returnDate(rentalEditModel.getReturnDate())
                .returned(rentalEditModel.isReturned())
                .vehicle(rental.getVehicle())
                .build();
    }
}

package org.example.vehicle.model.function;

import org.example.vehicle.entity.Rental;
import org.example.vehicle.model.RentalEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class RentalToEditModelFunciton implements Function<Rental, RentalEditModel>, Serializable {
    @Override
    public RentalEditModel apply(Rental rental) {
        return RentalEditModel.builder()
                .referenceCode(rental.getReferenceCode())
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .returned(rental.isReturned())
                .build();
    }
}

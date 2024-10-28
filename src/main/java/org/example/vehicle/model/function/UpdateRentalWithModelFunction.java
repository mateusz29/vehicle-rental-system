package org.example.vehicle.model.function;

import org.example.utils.DateUtils;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.RentalEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateRentalWithModelFunction implements BiFunction<Rental, RentalEditModel, Rental>, Serializable {
    @Override
    public Rental apply(Rental rental, RentalEditModel rentalEditModel) {
        return Rental.builder()
                .id(rental.getId())
                .referenceCode(rentalEditModel.getReferenceCode())
                .rentalDate(DateUtils.convertStringToLocalDate(rentalEditModel.getRentalDate()))
                .returnDate(DateUtils.convertStringToLocalDate(rentalEditModel.getReturnDate()))
                .returned(rentalEditModel.isReturned())
                .vehicle(Vehicle.builder()
                        .id(rentalEditModel.getVehicle().getId())
                        .build())
                .build();
    }
}

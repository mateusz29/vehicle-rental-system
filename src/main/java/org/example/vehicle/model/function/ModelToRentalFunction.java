package org.example.vehicle.model.function;

import org.example.utils.DateUtils;
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
                .referenceCode(model.getReferenceCode())
                .rentalDate(DateUtils.convertStringToLocalDate(model.getRentalDate()))
                .returnDate(DateUtils.convertStringToLocalDate(model.getReturnDate()))
                .returned(model.isReturned())
                .vehicle(Vehicle.builder()
                        .id(model.getVehicle().getId())
                        .build())
                .build();
    }
}

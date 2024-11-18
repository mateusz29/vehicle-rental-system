package org.example.vehicle.model.function;

import org.example.vehicle.entity.Rental;
import org.example.vehicle.model.RentalEditModel;
import org.example.vehicle.model.VehicleModel;

import java.io.Serializable;
import java.util.function.Function;

public class RentalToEditModelFunction implements Function<Rental, RentalEditModel>, Serializable {
    @Override
    public RentalEditModel apply(Rental rental) {
        return RentalEditModel.builder()
                .referenceCode(rental.getReferenceCode())
                .rentalDate(rental.getRentalDate() != null ? rental.getRentalDate().toString() : null)
                .returnDate(rental.getReturnDate() != null ? rental.getReturnDate().toString() : null)
                .returned(rental.isReturned())
                .vehicle(VehicleModel.builder()
                        .id(rental.getVehicle().getId())
                        .model(rental.getVehicle().getModel())
                        .brand(rental.getVehicle().getBrand())
                        .type(rental.getVehicle().getType())
                        .build())
                .build();
    }
}

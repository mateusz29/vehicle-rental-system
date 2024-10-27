package org.example.vehicle.dto.function;

import org.example.vehicle.dto.GetRentalResponse;
import org.example.vehicle.entity.Rental;

import java.util.function.Function;

public class RentalToResponseFunction implements Function<Rental, GetRentalResponse> {
    @Override
    public GetRentalResponse apply(Rental rental) {
        return GetRentalResponse.builder()
                .id(rental.getId())
                .referenceCode(rental.getReferenceCode())
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .returned(rental.isReturned())
                .vehicle(GetRentalResponse.Vehicle.builder()
                        .id(rental.getVehicle().getId())
                        .model(rental.getVehicle().getModel())
                        .brand(rental.getVehicle().getBrand())
                        .type(rental.getVehicle().getType())
                        .build())
                .build();
    }
}

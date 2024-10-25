package org.example.vehicle.dto.function;

import org.example.vehicle.dto.GetRentalResponse;
import org.example.vehicle.entity.Rental;

import java.util.function.Function;

public class RentalToResponseFunction implements Function<Rental, GetRentalResponse> {
    @Override
    public GetRentalResponse apply(Rental rental) {
        return GetRentalResponse.builder()
                .uuid(rental.getUuid())
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .returned(rental.isReturned())
                .vehicle(GetRentalResponse.Vehicle.builder()
                        .uuid(rental.getVehicle().getUuid())
                        .model(rental.getVehicle().getModel())
                        .brand(rental.getVehicle().getBrand())
                        .type(rental.getVehicle().getType())
                        .build())
                .build();
    }
}

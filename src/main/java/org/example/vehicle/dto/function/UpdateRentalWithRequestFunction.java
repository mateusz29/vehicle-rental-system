package org.example.vehicle.dto.function;

import org.example.vehicle.dto.PatchRentalRequest;
import org.example.vehicle.entity.Rental;

import java.util.function.BiFunction;

public class UpdateRentalWithRequestFunction implements BiFunction<Rental, PatchRentalRequest, Rental> {
    @Override
    public Rental apply(Rental rental, PatchRentalRequest patchRentalRequest) {
        return Rental.builder()
                .uuid(rental.getUuid())
                .rentalDate(patchRentalRequest.getRentalDate())
                .returnDate(patchRentalRequest.getReturnDate())
                .returned(patchRentalRequest.isReturned())
                .user(rental.getUser())
                .vehicle(rental.getVehicle())
                .build();
    }
}

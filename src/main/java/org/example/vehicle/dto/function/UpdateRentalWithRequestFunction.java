package org.example.vehicle.dto.function;

import org.example.vehicle.dto.PatchRentalRequest;
import org.example.vehicle.entity.Rental;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

public class UpdateRentalWithRequestFunction implements BiFunction<Rental, PatchRentalRequest, Rental> {
    @Override
    public Rental apply(Rental rental, PatchRentalRequest patchRentalRequest) {
        return Rental.builder()
                .id(rental.getId())
                .referenceCode(patchRentalRequest.getReferenceCode())
                .rentalDate(patchRentalRequest.getRentalDate())
                .returnDate(patchRentalRequest.getReturnDate())
                .returned(patchRentalRequest.isReturned())
                .user(rental.getUser())
                .vehicle(rental.getVehicle())
                .version(patchRentalRequest.getVersion())
                .creationDateTime(rental.getCreationDateTime())
                .updateDateTime(rental.getUpdateDateTime())
                .build();
    }
}

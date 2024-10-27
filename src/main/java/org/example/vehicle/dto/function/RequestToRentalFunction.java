package org.example.vehicle.dto.function;

import org.example.user.entity.User;
import org.example.vehicle.dto.PutRentalRequest;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToRentalFunction implements BiFunction<UUID, PutRentalRequest, Rental> {
    @Override
    public Rental apply(UUID id, PutRentalRequest request) {
        return Rental.builder()
                .id(id)
                .referenceCode(request.getReferenceCode())
                .rentalDate(request.getRentalDate())
                .returnDate(request.getReturnDate())
                .returned(request.isReturned())
                .vehicle(Vehicle.builder().id(request.getVehicle()).build())
                .user(User.builder().id(request.getUser()).build())
                .build();
    }
}

package org.example.vehicle.dto.function;

import org.example.user.entity.User;
import org.example.vehicle.dto.PutRentalRequest;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToRentalFunction implements BiFunction<UUID, PutRentalRequest, Rental> {
    @Override
    public Rental apply(UUID uuid, PutRentalRequest request) {
        return Rental.builder()
                .uuid(uuid)
                .rentalDate(request.getRentalDate())
                .returnDate(request.getReturnDate())
                .returned(request.isReturned())
                .vehicle(Vehicle.builder().uuid(request.getVehicle()).build())
                .user(User.builder().uuid(request.getUser()).build())
                .build();
    }
}

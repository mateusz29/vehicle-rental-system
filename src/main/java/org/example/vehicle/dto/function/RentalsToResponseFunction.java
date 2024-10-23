package org.example.vehicle.dto.function;

import org.example.vehicle.dto.GetRentalsResponse;
import org.example.vehicle.entity.Rental;

import java.util.List;
import java.util.function.Function;

public class RentalsToResponseFunction implements Function<List<Rental>, GetRentalsResponse> {
    @Override
    public GetRentalsResponse apply(List<Rental> rentals) {
        return GetRentalsResponse.builder()
                .rentals(rentals.stream()
                        .map(rental -> GetRentalsResponse.Rental.builder()
                                .uuid(rental.getUuid())
                                .rentalDate(rental.getRentalDate())
                                .returnDate(rental.getReturnDate())
                                .returned(rental.isReturned())
                                .build())
                        .toList())
                .build();
    }
}

package org.example.user.dto.function;

import org.example.user.dto.GetUserResponse;
import org.example.user.entity.User;

import java.util.function.Function;

public class UserToResponeFunction implements Function<User, GetUserResponse> {
    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .birthday(user.getBirthday())
                .rentals(user.getRentals().stream()
                        .map(rental -> GetUserResponse.Rental.builder()
                                .uuid(rental.getUuid())
                                .rentalDate(rental.getRentalDate())
                                .returnDate(rental.getReturnDate())
                                .returned(rental.isReturned())
                                .build())
                        .toList())
                .build();
    }
}

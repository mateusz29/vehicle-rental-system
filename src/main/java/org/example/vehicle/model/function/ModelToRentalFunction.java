package org.example.vehicle.model.function;

import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.RentalCreateModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class ModelToRentalFunction implements Function<RentalCreateModel, Rental>, Serializable {
    public static LocalDate convertStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(dateString, formatter);
        } catch (Exception e) {
            System.out.println("Invalid date format: " + dateString);
            return null;
        }
    }

    @Override
    public Rental apply(RentalCreateModel model) {
        return Rental.builder()
                .id(model.getId())
                .referenceCode(model.getReferenceCode())
                .rentalDate(convertStringToLocalDate(model.getRentalDate()))
                .returnDate(convertStringToLocalDate(model.getReturnDate()))
                .returned(model.isReturned())
                .vehicle(Vehicle.builder()
                        .id(model.getVehicle().getId())
                        .build())
                .build();
    }
}

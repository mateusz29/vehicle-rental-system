package org.example.vehicle.model.function;

import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.RentalEditModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;

public class UpdateRentalWithModelFunction implements BiFunction<Rental, RentalEditModel, Rental>, Serializable {
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
    public Rental apply(Rental rental, RentalEditModel rentalEditModel) {
        return Rental.builder()
                .id(rental.getId())
                .referenceCode(rentalEditModel.getReferenceCode())
                .rentalDate(convertStringToLocalDate(rentalEditModel.getRentalDate()))
                .returnDate(convertStringToLocalDate(rentalEditModel.getReturnDate()))
                .returned(rentalEditModel.isReturned())
                .vehicle(Vehicle.builder()
                        .id(rentalEditModel.getVehicle().getId())
                        .build())
                .build();
    }
}

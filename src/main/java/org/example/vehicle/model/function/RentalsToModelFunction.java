package org.example.vehicle.model.function;

import org.example.vehicle.entity.Rental;
import org.example.vehicle.model.RentalsModel;

import java.util.List;
import java.util.function.Function;

public class RentalsToModelFunction implements Function<List<Rental>, RentalsModel> {
    @Override
    public RentalsModel apply(List<Rental> entity) {
        return RentalsModel.builder()
                .rentals(entity.stream()
                        .map(rental -> RentalsModel.Rental.builder()
                                .id(rental.getId())
                                .referenceCode(rental.getReferenceCode())
                                .version(rental.getVersion())
                                .creationDateTime(rental.getCreationDateTime())
                                .updateDateTime(rental.getUpdateDateTime())
                                .build())
                        .toList())
                .build();
    }

}

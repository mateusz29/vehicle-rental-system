package org.example.vehicle.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.component.DtoFunctionFactory;
import org.example.controller.servlet.exception.NotFoundException;
import org.example.vehicle.controller.api.RentalController;
import org.example.vehicle.dto.GetRentalResponse;
import org.example.vehicle.dto.GetRentalsResponse;
import org.example.vehicle.dto.PutRentalRequest;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.service.RentalService;

import java.util.UUID;

@RequestScoped
public class RentalSimpleController implements RentalController {
    private final RentalService rentalService;
    private final DtoFunctionFactory factory;

    @Inject
    public RentalSimpleController(RentalService rentalService, DtoFunctionFactory factory) {
        this.rentalService = rentalService;
        this.factory = factory;
    }

    @Override
    public GetRentalResponse getRental(UUID uuid) {
        return rentalService.find(uuid)
                .map(factory.rentalToResponseFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetRentalsResponse getRentals() {
        return factory.rentalsToResponseFunction().apply(rentalService.findAll());
    }

    @Override
    public GetRentalsResponse getVehicleRentals(UUID uuid) {
        return rentalService.findAllByVehicle(uuid)
                .map(factory.rentalsToResponseFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetRentalsResponse getUserRentals(UUID uuid) {
        return rentalService.findAllByUser(uuid)
                .map(factory.rentalsToResponseFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putRental(UUID uuid, PutRentalRequest request) {
        Rental rental = factory.requestToRentalFunction().apply(uuid, request);

        if (rentalService.find(rental.getUuid()).isPresent()) {
            rentalService.update(rental);
        } else {
            rentalService.create(rental);
        }
    }

    @Override
    public void deleteRental(UUID uuid) {
        rentalService.find(uuid).ifPresentOrElse(rentalService::delete, () -> {
            throw new NotFoundException();
        });
    }
}

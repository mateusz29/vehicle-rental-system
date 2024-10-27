package org.example.vehicle.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.component.DtoFunctionFactory;
import org.example.controller.servlet.exception.BadRequestException;
import org.example.controller.servlet.exception.NotFoundException;
import org.example.vehicle.controller.api.RentalController;
import org.example.vehicle.dto.GetRentalResponse;
import org.example.vehicle.dto.GetRentalsResponse;
import org.example.vehicle.dto.PatchRentalRequest;
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
    public GetRentalResponse getRental(UUID id) {
        return rentalService.find(id)
                .map(factory.rentalToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetRentalsResponse getRentals() {
        return factory.rentalsToResponse().apply(rentalService.findAll());
    }

    @Override
    public GetRentalsResponse getVehicleRentals(UUID id) {
        return rentalService.findAllByVehicle(id)
                .map(factory.rentalsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetRentalsResponse getUserRentals(UUID id) {
        return rentalService.findAllByUser(id)
                .map(factory.rentalsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putRental(UUID id, PutRentalRequest request) {
        try {
            rentalService.create(factory.requestToRental().apply(id, request));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void updateRental(UUID id, PatchRentalRequest request) {
        rentalService.find(id).ifPresentOrElse(
                rental -> rentalService.update(factory.updateRental().apply(rental, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteRental(UUID id) {
        rentalService.find(id).ifPresentOrElse(
                vehicle -> rentalService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}

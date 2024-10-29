package org.example.vehicle.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import org.example.component.DtoFunctionFactory;
import org.example.vehicle.controller.api.RentalController;
import org.example.vehicle.dto.GetRentalResponse;
import org.example.vehicle.dto.GetRentalsResponse;
import org.example.vehicle.dto.PatchRentalRequest;
import org.example.vehicle.dto.PutRentalRequest;
import org.example.vehicle.service.RentalService;

import java.util.UUID;

@Path("")
public class RentalRestController implements RentalController {
    private final RentalService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public RentalRestController(
            RentalService service,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetRentalsResponse getRentals() {
        return factory.rentalsToResponse().apply(service.findAll());
    }

    @Override
    public GetRentalsResponse getVehicleRentals(UUID id) {
        return service.findAllByVehicle(id)
                .map(factory.rentalsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetRentalsResponse getUserRentals(UUID id) {
        return service.findAllByUser(id)
                .map(factory.rentalsToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetRentalResponse getRental(UUID id) {
        return service.find(id)
                .map(factory.rentalToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @SneakyThrows
    public void putRental(UUID id, PutRentalRequest request) {
        try {
            service.create(factory.requestToRental().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(RentalController.class, "getRental")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateRental(UUID id, PatchRentalRequest request) {
        service.find(id).ifPresentOrElse(
                rental -> service.update(factory.updateRental().apply(rental, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteRental(UUID id) {
        service.find(id).ifPresentOrElse(
                vehicle -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
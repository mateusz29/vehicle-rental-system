package org.example.vehicle.controller.rest;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.example.component.DtoFunctionFactory;
import org.example.vehicle.controller.api.RentalController;
import org.example.vehicle.dto.GetRentalResponse;
import org.example.vehicle.dto.GetRentalsResponse;
import org.example.vehicle.dto.PatchRentalRequest;
import org.example.vehicle.dto.PutRentalRequest;
import org.example.vehicle.service.RentalService;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class RentalRestController implements RentalController {
    private RentalService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public RentalRestController(
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(RentalService service) {
        this.service = service;
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
    public void putRental(UUID vehicleId, UUID rentalId, PutRentalRequest request) {
        try {
            service.create(factory.requestToRental().apply(rentalId, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(RentalController.class, "getRental")
                    .build(rentalId)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void updateRental(UUID vehicleId, UUID rentalId, PatchRentalRequest request) {
        try {
            service.find(rentalId).ifPresentOrElse(
                    rental -> {
                        try {
                            service.update(factory.updateRental().apply(rental, request));
                        } catch (EJBAccessException ex) {
                            log.log(Level.WARNING, ex.getMessage(), ex);
                            throw new ForbiddenException(ex.getMessage());
                        }
                    },
                    () -> {
                        throw new NotFoundException();
                    }
            );
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                throw new BadRequestException(ex.getCause());
            }
        }
    }

    @Override
    public void deleteRental(UUID id) {
        service.find(id).ifPresentOrElse(
                vehicle -> {
                    try {
                        service.delete(id);
                    } catch (EJBAccessException ex) {
                        log.log(Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
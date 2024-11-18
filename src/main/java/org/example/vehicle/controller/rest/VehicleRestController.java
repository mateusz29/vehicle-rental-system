package org.example.vehicle.controller.rest;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import org.example.component.DtoFunctionFactory;
import org.example.vehicle.controller.api.VehicleController;
import org.example.vehicle.dto.GetVehicleResponse;
import org.example.vehicle.dto.GetVehiclesResponse;
import org.example.vehicle.dto.PatchVehicleRequest;
import org.example.vehicle.dto.PutVehicleRequest;
import org.example.vehicle.service.VehicleService;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class VehicleRestController implements VehicleController {
    private VehicleService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public VehicleRestController(
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(VehicleService service) {
        this.service = service;
    }

    @Override
    public GetVehiclesResponse getVehicles() {
        return factory.vehiclesToResponse().apply(service.findAll());
    }

    @Override
    public GetVehicleResponse getVehicle(UUID id) {
        return service.find(id)
                .map(factory.vehicleToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putVehicle(UUID id, PutVehicleRequest request) {
        try {
            service.create(factory.requestToVehicle().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(VehicleController.class, "getVehicle")
                    .build(id)
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
    public void updateVehicle(UUID id, PatchVehicleRequest request) {
        service.find(id).ifPresentOrElse(
                vehicle -> {
                    try {
                        service.update(factory.updateVehicle().apply(vehicle, request));
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

    @Override
    public void deleteVehicle(UUID id) {
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

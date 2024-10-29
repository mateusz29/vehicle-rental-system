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
import org.example.component.DtoFunctionFactory;
import org.example.vehicle.controller.api.VehicleController;
import org.example.vehicle.dto.GetVehicleResponse;
import org.example.vehicle.dto.GetVehiclesResponse;
import org.example.vehicle.dto.PatchVehicleRequest;
import org.example.vehicle.dto.PutVehicleRequest;
import org.example.vehicle.service.VehicleService;

import java.util.UUID;

@Path("")
public class VehicleRestController implements VehicleController {
    private final VehicleService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public VehicleRestController(
            VehicleService service,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
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
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateVehicle(UUID id, PatchVehicleRequest request) {
        service.find(id).ifPresentOrElse(
                vehicle -> service.update(factory.updateVehicle().apply(vehicle, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteVehicle(UUID id) {
        service.find(id).ifPresentOrElse(
                vehicle -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}

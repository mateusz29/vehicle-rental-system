package org.example.vehicle.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.vehicle.dto.GetVehicleResponse;
import org.example.vehicle.dto.GetVehiclesResponse;
import org.example.vehicle.dto.PatchVehicleRequest;
import org.example.vehicle.dto.PutVehicleRequest;

import java.util.UUID;

public interface VehicleController {

    @GET
    @Path("/vehicles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetVehicleResponse getVehicle(@PathParam("id") UUID id);

    @GET
    @Path("/vehicles")
    @Produces(MediaType.APPLICATION_JSON)
    GetVehiclesResponse getVehicles();

    @PUT
    @Path("/vehicles/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putVehicle(@PathParam("id") UUID id, PutVehicleRequest request);

    @PATCH
    @Path("/vehicles/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateVehicle(@PathParam("id") UUID id, PatchVehicleRequest request);

    @DELETE
    @Path("/vehicles/{id}")
    void deleteVehicle(@PathParam("id") UUID id);
}

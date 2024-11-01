package org.example.vehicle.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.vehicle.dto.GetRentalResponse;
import org.example.vehicle.dto.GetRentalsResponse;
import org.example.vehicle.dto.PatchRentalRequest;
import org.example.vehicle.dto.PutRentalRequest;

import java.util.UUID;

@Path("")
public interface RentalController {

    @GET
    @Path("/rentals/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetRentalResponse getRental(@PathParam("id") UUID id);

    @GET
    @Path("/rentals")
    @Produces(MediaType.APPLICATION_JSON)
    GetRentalsResponse getRentals();

    @GET
    @Path("/vehicles/{id}/rentals")
    @Produces(MediaType.APPLICATION_JSON)
    GetRentalsResponse getVehicleRentals(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/rentals")
    @Produces(MediaType.APPLICATION_JSON)
    GetRentalsResponse getUserRentals(@PathParam("id") UUID id);

    @PUT
    @Path("/vehicles/{vehicleId}/rentals/{rentalId}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putRental(@PathParam("vehicleId") UUID vehicleId, @PathParam("rentalId") UUID rentalId ,PutRentalRequest request);

    @PATCH
    @Path("/vehicles/{vehicleId}/rentals/{rentalId}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateRental(@PathParam("vehicleId") UUID vehicleId, @PathParam("rentalId") UUID rentalId, PatchRentalRequest request);

    @DELETE
    @Path("/rentals/{id}")
    void deleteRental(@PathParam("id") UUID id);
}
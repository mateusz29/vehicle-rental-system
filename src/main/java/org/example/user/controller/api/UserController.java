package org.example.user.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.user.dto.GetUserResponse;
import org.example.user.dto.GetUsersResponse;
import org.example.user.dto.PatchUserRequest;
import org.example.user.dto.PutUserRequest;

import java.util.UUID;

@Path("")
public interface UserController {

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    @PUT
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putUser(@PathParam("id") UUID id, PutUserRequest request);

    @PATCH
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateUser(@PathParam("id") UUID id, PatchUserRequest request);

    @DELETE
    @Path("/users/{id}")
    void deleteUser(@PathParam("id") UUID id);
}

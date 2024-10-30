package org.example.user.avatar.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface AvatarController {

    @GET
    @Path("/users/{id}/avatar")
    @Produces("image/png")
    byte[] getAvatar(@PathParam("id") UUID id);

    // doesn't work properly, it spits out corrupted image
    @PUT
    @Path("/users/{id}/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void putAvatar(@PathParam("id") UUID id, InputStream is);

    @DELETE
    @Path("/users/{id}/avatar")
    void deleteAvatar(@PathParam("id") UUID id);
}

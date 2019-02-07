package net.halflite.resteasy.web.resource;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/")
public class IndexResource {

    @GET
    public Response index() {
        return Response.ok()
                .entity("Hello, World")
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build();
    }
}

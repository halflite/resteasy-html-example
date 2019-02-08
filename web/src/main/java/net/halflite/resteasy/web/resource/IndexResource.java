package net.halflite.resteasy.web.resource;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.jboss.resteasy.plugins.providers.html.Renderable;
import org.jboss.resteasy.plugins.providers.html.View;

@Singleton
@Path("/")
public class IndexResource {

    @GET
    public Renderable index() {
        return new View("index.ftl");
    }
}

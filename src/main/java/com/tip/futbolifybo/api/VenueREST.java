package com.tip.futbolifybo.api;

import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.VenueResponse;
import com.tip.futbolifybo.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("venue")
public class VenueREST {

    @Autowired
    private VenueService venueService;

    @POST
    @Path("register")
    @Produces("application/json;charset=utf-8")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Response register(@FormParam("code") String code,
                             @FormParam("name") String name) {

        GenericResponse response = this.venueService.add(code, name);
        return Response.ok(response).build();
    }


    @GET
    @Path("list")
    @Produces("application/json;charset=utf-8")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Response list() {
        List<VenueResponse> venues = this.venueService.list();
        return Response.ok(venues).build();
    }

    @GET
    @Path("list_with_track")
    @Produces("application/json;charset=utf-8")
    public Response listWithTrack() {
        List<VenueResponse> venues = this.venueService.listWithTrack();
        return Response.ok(venues).build();
    }

}

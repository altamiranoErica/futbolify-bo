package com.tip.futbolifybo.api;

import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.SpaceResponse;
import com.tip.futbolifybo.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("space")
public class SpaceREST {

    @Autowired
    private SpaceService spaceService;

    @POST
    @Path("register")
    @Produces("application/json;charset=utf-8")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Response register(@FormParam("code") String code,
                             @FormParam("name") String name) {

        GenericResponse response = this.spaceService.add(code, name);
        return Response.ok(response).build();
    }


    @GET
    @Path("list")
    @Produces("application/json;charset=utf-8")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Response list() {
        List<SpaceResponse> spaces = this.spaceService.list();
        return Response.ok(spaces).build();
    }

    @GET
    @Path("list_with_track")
    @Produces("application/json;charset=utf-8")
    public Response listWithTrack() {
        List<SpaceResponse> spaces = this.spaceService.listWithTrack();
        return Response.ok(spaces).build();
    }

}

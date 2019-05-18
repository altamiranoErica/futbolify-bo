package com.tip.futbolifybo.api;

import com.tip.futbolifybo.api.response.TrackResponse;
import com.tip.futbolifybo.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("track")
public class TrackREST {

    @Autowired
    private TrackService trackService;

    @GET
    @Path("search")
    @Produces("application/json;charset=utf-8")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Response search(@QueryParam("query") String query,
                           @QueryParam("page") Integer page) {
        List<TrackResponse> tracks = this.trackService.search(query, page);
        return Response.ok(tracks).build();
    }

}

package com.tip.futbolifybo.api;

import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.PollResponse;
import com.tip.futbolifybo.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("poll")
public class PollREST {

    @Autowired
    private PollService pollService;

    @POST
    @Path("new")
    @Produces("application/json;charset=utf-8")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Response add(@FormParam("data") String json) {
        GenericResponse response = this.pollService.add(json);
        return Response.ok(response).build();
    }

    @GET
    @Path("active_list")
    @Produces("application/json;charset=utf-8")
    public Response list() {
        List<PollResponse> response = this.pollService.list();
        return Response.ok(response).build();
    }

    @POST
    @Path("vote")
    @Produces("application/json;charset=utf-8")
    public Response vote(@FormParam("pollID") String pollID,
                         @FormParam("trackID") String trackID) {
        PollResponse response = this.pollService.vote(pollID, trackID);
        if(response == null){
            GenericResponse error = new GenericResponse("ERROR", "Selected track does not exist!");
            return Response.ok(error).status(406).build();
        }

        return Response.ok(response).build();
    }
}

package com.tip.futbolifybo.api;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Component
@Path("authorize")
public class AuthorizeREST {

    @GET
    @Path("login")
    @Produces("application/json;charset=utf-8")
    public Response register(@QueryParam("token") String code) {
        String response = "{ 'message' : 'OK' }";
        return Response.ok(response).build();
    }
}

package com.tip.futbolifybo.api;

import com.tip.futbolifybo.api.response.UserResponse;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Path("authorize")
public class AuthorizeREST {

    @POST
    @Path("login")
    @Produces("application/json;charset=utf-8")
    public Response register(@FormParam("username") String username,
                             @FormParam("password") String password) {
        UserResponse response = new UserResponse("eyJhbGciOiJIUzI1NiJ");
        return Response.ok(response).build();
    }
}

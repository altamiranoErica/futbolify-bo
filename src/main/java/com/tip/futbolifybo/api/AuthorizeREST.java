package com.tip.futbolifybo.api;

import com.tip.futbolifybo.api.response.GenericResponse;
import com.tip.futbolifybo.api.response.UserResponse;
import com.tip.futbolifybo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Path("authorize")
public class AuthorizeREST {

    @Autowired
    private UserService userService;

    @POST
    @Path("login")
    @Produces("application/json;charset=utf-8")
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password) {
        UserResponse response = new UserResponse("eyJhbGciOiJIUzI1NiJ");
        return Response.ok(response).build();
    }

    @POST
    @Path("register")
    @Produces("application/json;charset=utf-8")
    public Response register(@FormParam("username") String username,
                             @FormParam("password") String password) {
        GenericResponse response = this.userService.add(username, password);
        return Response.ok(response).build();
    }

}

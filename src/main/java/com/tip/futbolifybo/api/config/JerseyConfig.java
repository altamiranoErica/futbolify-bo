package com.tip.futbolifybo.api.config;

import com.tip.futbolifybo.api.AuthorizeREST;
import com.tip.futbolifybo.api.CORSResponseFilter;
import com.tip.futbolifybo.api.SpaceREST;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * Jersey configuration class.
 *
 * @author cassiomolin
 */
@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(CORSResponseFilter.class);

        register(SpaceREST.class);
        register(AuthorizeREST.class);
    }
}
package com.tip.futbolifybo.api.config;

import com.tip.futbolifybo.api.AuthorizeREST;
import com.tip.futbolifybo.api.CORSResponseFilter;
import com.tip.futbolifybo.api.SpaceREST;
import com.tip.futbolifybo.api.TrackREST;
import com.tip.futbolifybo.security.api.exceptionmapper.AccessDeniedExceptionMapper;
import com.tip.futbolifybo.security.api.exceptionmapper.AuthenticationExceptionMapper;
import com.tip.futbolifybo.security.api.exceptionmapper.AuthenticationTokenRefreshmentExceptionMapper;
import com.tip.futbolifybo.security.api.resource.AuthenticationResource;
import com.tip.futbolifybo.service.provider.ObjectMapperProvider;
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
        register(TrackREST.class);
        register(AuthorizeREST.class);

        register(AccessDeniedExceptionMapper.class);
        register(AuthenticationExceptionMapper.class);
        register(AuthenticationTokenRefreshmentExceptionMapper.class);
        register(AuthenticationResource.class);

        register(ObjectMapperProvider.class);

    }
}
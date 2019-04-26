package com.tip.futbolifybo.api.config;

import com.tip.futbolifybo.api.AuthorizeREST;
import com.tip.futbolifybo.api.CORSResponseFilter;
import com.tip.futbolifybo.api.SpaceREST;
import com.tip.futbolifybo.api.provider.ObjectMapperProvider;
import com.tip.futbolifybo.security.api.exceptionmapper.AccessDeniedExceptionMapper;
import com.tip.futbolifybo.security.api.exceptionmapper.AuthenticationExceptionMapper;
import com.tip.futbolifybo.security.api.exceptionmapper.AuthenticationTokenRefreshmentExceptionMapper;
import com.tip.futbolifybo.security.api.resource.AuthenticationResource;
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
        register(AuthenticationResource.class);

        register(AccessDeniedExceptionMapper.class);
        register(AuthenticationExceptionMapper.class);
        register(AuthenticationTokenRefreshmentExceptionMapper.class);
        register(ObjectMapperProvider.class);

        register(SpaceREST.class);
        register(AuthorizeREST.class);
    }
}
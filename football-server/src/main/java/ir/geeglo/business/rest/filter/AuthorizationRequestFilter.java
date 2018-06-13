package ir.geeglo.business.rest.filter;

import ir.piana.dev.webtool2.server.annotation.PianaFilter;
import ir.piana.dev.webtool2.server.annotation.PianaServer;
import ir.piana.dev.webtool2.server.annotation.PianaServerCORS;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import java.io.IOException;

@Singleton
@PianaFilter
public class AuthorizationRequestFilter implements ContainerRequestFilter {
    @Context
    private Configuration config;

    private PianaServer pianaServer = null;
    private PianaServerCORS serverCORS = null;

    @PostConstruct
    public void init() {
        pianaServer = (PianaServer) config
                .getProperty("PIANA_SERVER_CONFIG");
        serverCORS = pianaServer.serverCORS();
    }
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        System.out.println("get request");
    }
}

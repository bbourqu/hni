package org.hni.admin.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.hni.admin.service.OrganizationServiceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizationRequestFilter implements ContainerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(AuthorizationRequestFilter.class);
	
	private static final String TOKEN_HEADER = "X-hni-token";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		System.out.println("PATH = "+requestContext.getUriInfo().getPath());
		if (requestContext.getUriInfo().getPath().contains("/security/authentication")) {
			return; // allow requests to /auth to pass through
		}
		
        // TODO: check header for valid access token
        if (requestContext.getHeaders().containsKey(TOKEN_HEADER)) {
        	// TODO: check header for valid access token
        	// TODO: bind user context to thread
        	System.out.println("token="+requestContext.getHeaders().get(TOKEN_HEADER));
        	return;
        }
        requestContext.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("User cannot access the resource.")
                .build());
	}


}

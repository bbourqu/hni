package org.hni.admin;

import javax.ws.rs.ApplicationPath;

import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.hni.admin.configuration.AdminConfiguration;
import org.hni.admin.filter.ResponseCorsFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("api/v1") /* this is the servlet mapping */
public class Application extends ResourceConfig {

	private static final String DEFAULT_HOST = "http://localhost:8080";
	private static final String DEFAULT_VERSION = "1.5.0";
	private static final String DEFAULT_BASEPATH = "/hni-admin-service/api/v1";
	private static final String DEFAULT_SCHEME = "http";
	private static final String DEFAULT_RESOURCE_PACKAGE = "org.hni";
	
    public Application() {
        packages("org.hni");
        register(JacksonFeature.class);
        register(ResponseCorsFilter.class);
        
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        ApplicationContext rootCtx = new AnnotationConfigApplicationContext(AdminConfiguration.class);
        Environment environment = rootCtx.getBean(Environment.class);
        
        String host = StringUtils.defaultIfBlank(environment.getProperty("swagger.host"), DEFAULT_HOST);
        String version = StringUtils.defaultIfBlank(environment.getProperty("swagger.version"), DEFAULT_VERSION);
        String basePath = StringUtils.defaultIfBlank(environment.getProperty("swagger.basePath"), DEFAULT_BASEPATH);
        String scheme = StringUtils.defaultIfBlank(environment.getProperty("swagger.scheme"), DEFAULT_SCHEME);
 
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(version);
        beanConfig.setSchemes(new String[]{scheme});
        beanConfig.setHost(host);
        beanConfig.setBasePath(basePath);
        beanConfig.setResourcePackage(DEFAULT_RESOURCE_PACKAGE);
        beanConfig.setScan(true);
    }
}

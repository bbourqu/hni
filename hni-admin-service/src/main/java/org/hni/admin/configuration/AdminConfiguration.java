package org.hni.admin.configuration;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * you MUST define a spring profile to activate in your Java env when launching
 * the servlet container e.g. -Dspring.profiles.active=local-dev
 * 
 */
//@Configuration
//@ComponentScan("org.hni")
//@Import(DaoConfiguration.class)
public class AdminConfiguration implements ApplicationContextAware  {

	private ApplicationContext applicationContext;

	@Profile({ "production", "localdev", "remotedev" })
	@Bean(name = "dataSource", destroyMethod = "close")
	public DataSource hikariDataSource() {
		Environment environment = applicationContext.getEnvironment();
		
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setPoolName("springHikariCP");
		hikariConfig.setConnectionTestQuery(environment.getProperty("dataSource.connectionTestQuery"));
		hikariConfig.setDataSourceClassName(environment.getProperty("dataSource.databaseName"));
		hikariConfig.setMaximumPoolSize(5);
		hikariConfig.addDataSourceProperty("user",  environment.getProperty("dataSource.username"));
		hikariConfig.addDataSourceProperty("password",  environment.getProperty("dataSource.password"));
		hikariConfig.addDataSourceProperty("databaseName", environment.getProperty("dataSource.dataSourceClassName"));
		hikariConfig.addDataSourceProperty("portNumber", environment.getProperty("dataSource.portNumber"));
		hikariConfig.addDataSourceProperty("serverName",  environment.getProperty("dataSource.serverName"));
		
		return new HikariDataSource(hikariConfig);
	}

	@Profile("integration")
	@Bean(name = "dataSource")
	public DataSource embeddedDataSource() {
		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:hsql/schema.sql")
				.addScript("classpath:hsql/test-data.sql")
				.build();
		return db;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

}

package com.example.demo.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by damz on 6/2/2017.
 */
@Configuration
@Profile(value="h2")
public class H2DataSourceConfig implements DataSourceConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @FlywayDataSource
    @Override
    public DataSource dataSource() {
        logger.info("**** Initializing H2:");
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("demo")
                .build();
        return db;
    }

    /**@Bean(destroyMethod = "stop")
    public Server h2WebServer() throws SQLException {
        Server dbServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
        dbServer.start();
        return dbServer;
    }**/
}
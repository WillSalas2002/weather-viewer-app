package com.will.configuration;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class LiquibaseConfig {

    private final Environment ENV;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:" + ENV.getProperty("liquibase.changelog"));
        return liquibase;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName((Objects.requireNonNull(ENV.getProperty("db.driver"))));
        dataSource.setUrl(ENV.getProperty("db.url"));
        dataSource.setUsername(ENV.getProperty("db.username"));
        dataSource.setPassword(ENV.getProperty("db.password"));
        return dataSource;
    }
}

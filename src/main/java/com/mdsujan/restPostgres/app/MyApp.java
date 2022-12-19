package com.mdsujan.restPostgres.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication // defines it as the main class for running the app
@ComponentScan("com.mdsujan.restPostgres") // the pkg that contain components
@EntityScan("com.mdsujan.restPostgres.entity")
@EnableJpaRepositories("com.mdsujan.restPostgres.repository")
@EnableCaching
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }
}

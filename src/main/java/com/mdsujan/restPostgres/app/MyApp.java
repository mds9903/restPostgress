package com.mdsujan.restPostgres.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.mdsujan.restPostgres.service", "com.mdsujan.restPostgres.controller", "com.mdsujan.restPostgres.exceptionHandling"})
@EntityScan("com.mdsujan.restPostgres.entity")
@EnableJpaRepositories("com.mdsujan.restPostgres.repository")
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

}

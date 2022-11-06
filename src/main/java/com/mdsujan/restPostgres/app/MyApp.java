package com.mdsujan.restPostgres.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.mdsujan.restPostgres.service", "com.mdsujan.restPostgres.controller"})
@EntityScan("com.mdsujan.restPostgres.entity")
public class MyApp {

    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

}

package com.mdsujan.restProges.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.mdsujan.restProges.service", "com.mdsujan.restProges.controller"})
@EntityScan("com.mdsujan.restPostgres.entity")
public class MyApp {

    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

}

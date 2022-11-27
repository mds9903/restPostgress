package com.mdsujan.restPostgres;

import com.mdsujan.restPostgres.service.ItemService;
import com.mdsujan.restPostgres.service.LocationService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class LocationServiceTestContextConfig {

    @Bean
    public LocationService itemService() {
        return new LocationService() {
            // implement methods 
        };
    }
}
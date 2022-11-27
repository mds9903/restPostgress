package com.mdsujan.restPostgres;

import com.mdsujan.restPostgres.service.ItemService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ItemServiceTestContextConfig {

    @Bean
    public ItemService itemService() {
        return new ItemService() {
            // implement methods 
        };
    }
}
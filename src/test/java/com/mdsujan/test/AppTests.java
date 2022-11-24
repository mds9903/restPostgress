package com.mdsujan.test;

import com.mdsujan.restPostgres.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

@SpringBootTest
public class AppTests {
    @Autowired
    Item item;

    @BeforeEach
    void testSetup(){
        
    }

    @Test
    void basicTest(){

    }

}

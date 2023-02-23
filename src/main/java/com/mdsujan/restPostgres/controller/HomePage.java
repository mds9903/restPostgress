package com.mdsujan.restPostgres.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomePage {

    @GetMapping
    public String getHomePage() {
        return ("<h1>Welcome<h1/>");
    }
}

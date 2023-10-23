package com.nhomA.mockproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String getHome() {
        return "Welcome home";
    }

    @PostMapping
    public String postHome() {
        return "Welcome home using POST HTTP Method";
    }
}

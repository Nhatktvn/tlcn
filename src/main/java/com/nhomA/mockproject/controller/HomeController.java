package com.nhomA.mockproject.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@CrossOrigin
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

package com.onevoice.management.onevoice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login-choice.html")
    public String loginChoice() {
        return "login-choice";
    }

    @GetMapping("/individual-login.html")
    public String individualLogin() {
        return "individual-login";
    }

    @GetMapping("/panchayath-login.html")
    public String panchayathLogin() {
        return "panchayath-login";
    }
}


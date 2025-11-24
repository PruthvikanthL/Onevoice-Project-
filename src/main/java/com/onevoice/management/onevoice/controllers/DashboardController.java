package com.onevoice.management.onevoice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/individuals/dashboard")
    public String individualDashboard() {
        return "individual-dashboard";
    }

    @GetMapping("/panchayath/dashboard")
    public String panchayathDashboard() {
        return "panchayath-dashboard";
    }
}


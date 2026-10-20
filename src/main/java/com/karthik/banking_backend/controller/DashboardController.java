package com.karthik.banking_backend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karthik.banking_backend.dto.DashboardResponse;
import com.karthik.banking_backend.service.DashboardService;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/api/dashboard")
    public DashboardResponse dashboard(Principal principal) {

        return dashboardService.getDashboard(principal.getName());
    }
}
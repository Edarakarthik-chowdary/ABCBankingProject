package com.karthik.banking_backend.service;

import com.karthik.banking_backend.dto.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard(String email);

}
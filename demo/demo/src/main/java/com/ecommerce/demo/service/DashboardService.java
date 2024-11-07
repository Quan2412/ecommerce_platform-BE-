package com.ecommerce.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecommerce.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;    

    public Map<String, Object> getUserDashboardStats() {
        Map<String, Object> dashboardStats = new HashMap<>();
        dashboardStats.put("topUsers", userRepository.findUserDashboardStats());
        
        return dashboardStats;
    }
}

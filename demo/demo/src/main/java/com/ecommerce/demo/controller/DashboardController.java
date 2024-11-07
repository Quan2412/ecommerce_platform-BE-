package com.ecommerce.demo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try{
        Map<String, Object> dashboardStats = dashboardService.getUserDashboardStats();
        return ResponseEntity.ok(dashboardStats);
        }catch(Exception e){
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to fetch dashboard stats"));
        }
    }

    
}

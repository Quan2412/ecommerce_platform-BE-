package com.ecommerce.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.demo.dto.DashboardDTO;
import com.ecommerce.demo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardDTO> getDashboardStats() {
        try {
            DashboardDTO dashboardStats = dashboardService.getDashboardStats();
            return ResponseEntity.ok(dashboardStats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
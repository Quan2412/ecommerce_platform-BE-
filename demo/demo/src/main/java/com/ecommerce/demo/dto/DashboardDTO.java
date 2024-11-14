package com.ecommerce.demo.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DashboardDTO {
    private String username;
    private long totalPurchases;  
    private String mostBoughtProduct;
    private String mostSoldProduct;
    private double totalRevenue;
    private List<UserStatDTO> topUsers;
    private Map<String, Object> statistics;

    public DashboardDTO() {
    }

    public DashboardDTO(String username, long totalPurchases, String mostBoughtProduct, String mostSoldProduct, double totalRevenue, List<UserStatDTO> topUsers, Map<String,Object> statistics) {
        this.username = username;
        this.totalPurchases = totalPurchases;
        this.mostBoughtProduct = mostBoughtProduct;
        this.mostSoldProduct = mostSoldProduct;
        this.totalRevenue = totalRevenue;
        this.topUsers = topUsers;
        this.statistics = statistics;
    }
    

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTotalPurchases() {
        return this.totalPurchases;
    }

    public void setTotalPurchases(long totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    public String getMostBoughtProduct() {
        return this.mostBoughtProduct;
    }

    public void setMostBoughtProduct(String mostBoughtProduct) {
        this.mostBoughtProduct = mostBoughtProduct;
    }

    public String getMostSoldProduct() {
        return this.mostSoldProduct;
    }

    public void setMostSoldProduct(String mostSoldProduct) {
        this.mostSoldProduct = mostSoldProduct;
    }

    public double getTotalRevenue() {
        return this.totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public List<UserStatDTO> getTopUsers() {
        return this.topUsers;
    }

    public void setTopUsers(List<UserStatDTO> topUsers) {
        this.topUsers = topUsers;
    }

    public Map<String,Object> getStatistics() {
        return this.statistics;
    }

    public void setStatistics(Map<String,Object> statistics) {
        this.statistics = statistics;
    }

}
package com.ecommerce.demo.dto;

import lombok.Data;

@Data
public class UserDashboardDTO {
    private String username;
    private long totalPurchases;  // Changed to long for COUNT result
    private String mostBoughtProduct;
    private String mostSoldProduct;
    private double totalRevenue;

    // Add this constructor
    public UserDashboardDTO(String username, long totalPurchases, 
                          String mostBoughtProduct, String mostSoldProduct, 
                          double totalRevenue) {
        this.username = username;
        this.totalPurchases = totalPurchases;
        this.mostBoughtProduct = mostBoughtProduct;
        this.mostSoldProduct = mostSoldProduct;
        this.totalRevenue = totalRevenue;
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

}
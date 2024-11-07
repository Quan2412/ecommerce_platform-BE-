package com.ecommerce.demo.dto;

import lombok.Data;

@Data
public class UserDashboardDTO {
    private String username;
    private int totalPurchases;
    private String mostBoughtProduct;
    private String mostSoldProduct;
    private double totalRevenue;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalPurchases() {
        return this.totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
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
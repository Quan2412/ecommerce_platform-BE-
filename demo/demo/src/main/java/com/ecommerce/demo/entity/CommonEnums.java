package com.ecommerce.demo.entity;

public class CommonEnums {
    
    public enum UserRole {
        GUEST,
        USER,
        SELLER,
        ADMIN
    }

    public enum OrderStatus {
        PENDING,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REFUNDED
    }

    public enum ShippingStatus {
        PENDING,
        IN_TRANSIT,
        DELIVERED
    }

    public enum AttributeType {
        SIZE,
        COLOR,
        WEIGHT,
        MATERIAL,
        OTHER
    }
}
package com.ecommerce.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Shipping")
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShippingID")
    private Long shippingId;

    @OneToOne
    @JoinColumn(name = "OrderID", nullable = false)
    private Order order;

    @Column(name = "CarrierName")
    private String carrierName;

    @Column(name = "TrackingNumber")
    private String trackingNumber;

    @Column(name = "ShippingDate")
    private LocalDateTime shippingDate;

    @Column(name = "EstimatedDeliveryDate")
    private LocalDateTime estimatedDeliveryDate;

    @Column(name = "ActualDeliveryDate")
    private LocalDateTime actualDeliveryDate;

    @Column(name = "Status", nullable = false)
    private String status;

    public Shipping() {
    }

    public Shipping(Long shippingId, Order order, String carrierName, String trackingNumber, LocalDateTime shippingDate, LocalDateTime estimatedDeliveryDate, LocalDateTime actualDeliveryDate, String status) {
        this.shippingId = shippingId;
        this.order = order;
        this.carrierName = carrierName;
        this.trackingNumber = trackingNumber;
        this.shippingDate = shippingDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.actualDeliveryDate = actualDeliveryDate;
        this.status = status;
    }

    public Long getShippingId() {
        return this.shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCarrierName() {
        return this.carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public LocalDateTime getShippingDate() {
        return this.shippingDate;
    }

    public void setShippingDate(LocalDateTime shippingDate) {
        this.shippingDate = shippingDate;
    }

    public LocalDateTime getEstimatedDeliveryDate() {
        return this.estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public LocalDateTime getActualDeliveryDate() {
        return this.actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDateTime actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
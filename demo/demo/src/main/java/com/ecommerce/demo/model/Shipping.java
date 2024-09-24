package com.ecommerce.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Shipping")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShippingID")
    private Integer shippingId;

    @OneToOne
    @JoinColumn(name = "OrderID", nullable = false)
    private Order order;

    @Column(name = "CarrierName", length = 100)
    private String carrierName;

    @Column(name = "TrackingNumber", length = 100)
    private String trackingNumber;

    @Column(name = "ShippingDate")
    private LocalDateTime shippingDate;

    @Column(name = "EstimatedDeliveryDate")
    private LocalDate estimatedDeliveryDate;

    @Column(name = "ActualDeliveryDate")
    private LocalDate actualDeliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 20)
    private ShippingStatus status;

    public enum ShippingStatus {
        PENDING, IN_TRANSIT, DELIVERED
    }


    public Shipping() {
    }
    

    public Shipping(Integer shippingId, Order order, String carrierName, String trackingNumber, LocalDateTime shippingDate, LocalDate estimatedDeliveryDate, LocalDate actualDeliveryDate, ShippingStatus status) {
        this.shippingId = shippingId;
        this.order = order;
        this.carrierName = carrierName;
        this.trackingNumber = trackingNumber;
        this.shippingDate = shippingDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.actualDeliveryDate = actualDeliveryDate;
        this.status = status;
    }


    /**
     * @return Integer return the shippingId
     */
    public Integer getShippingId() {
        return shippingId;
    }

    /**
     * @param shippingId the shippingId to set
     */
    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    /**
     * @return Order return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * @return String return the carrierName
     */
    public String getCarrierName() {
        return carrierName;
    }

    /**
     * @param carrierName the carrierName to set
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    /**
     * @return String return the trackingNumber
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * @param trackingNumber the trackingNumber to set
     */
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    /**
     * @return LocalDateTime return the shippingDate
     */
    public LocalDateTime getShippingDate() {
        return shippingDate;
    }

    /**
     * @param shippingDate the shippingDate to set
     */
    public void setShippingDate(LocalDateTime shippingDate) {
        this.shippingDate = shippingDate;
    }

    /**
     * @return LocalDate return the estimatedDeliveryDate
     */
    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    /**
     * @param estimatedDeliveryDate the estimatedDeliveryDate to set
     */
    public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    /**
     * @return LocalDate return the actualDeliveryDate
     */
    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    /**
     * @param actualDeliveryDate the actualDeliveryDate to set
     */
    public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    /**
     * @return ShippingStatus return the status
     */
    public ShippingStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(ShippingStatus status) {
        this.status = status;
    }

}
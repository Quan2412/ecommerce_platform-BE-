package com.ecommerce.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RefundID")
    private Long refundId;

    @ManyToOne
    @JoinColumn(name = "PaymentID", nullable = false)
    private Payment payment;

    @Column(name = "Amount", nullable = false)
    private Double amount;

    @Column(name = "Reason")
    private String reason;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "RefundDate")
    private LocalDateTime refundDate;

    public Refund() {
    }

    public Refund(Long refundId, Payment payment, Double amount, String reason, String status, LocalDateTime refundDate) {
        this.refundId = refundId;
        this.payment = payment;
        this.amount = amount;
        this.reason = reason;
        this.status = status;
        this.refundDate = refundDate;
    }

    public Long getRefundId() {
        return this.refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRefundDate() {
        return this.refundDate;
    }

    public void setRefundDate(LocalDateTime refundDate) {
        this.refundDate = refundDate;
    }
    
}
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
@Table(name = "PaymentAttempt")
public class PaymentAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentAttemptID")
    private Long paymentAttemptId;

    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PaymentMethodID", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "Amount", nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "CurrencyID", nullable = false)
    private Currency currency;

    @Column(name = "ExchangeRate", nullable = false)
    private Double exchangeRate;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "TransactionID")
    private String transactionId;

    @Column(name = "AttemptDate")
    private LocalDateTime attemptDate;

    public PaymentAttempt() {
    }

    public PaymentAttempt(Long paymentAttemptId, Order order, PaymentMethod paymentMethod, Double amount, Currency currency, Double exchangeRate, String status, String transactionId, LocalDateTime attemptDate) {
        this.paymentAttemptId = paymentAttemptId;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.status = status;
        this.transactionId = transactionId;
        this.attemptDate = attemptDate;
    }

    public Long getPaymentAttemptId() {
        return this.paymentAttemptId;
    }

    public void setPaymentAttemptId(Long paymentAttemptId) {
        this.paymentAttemptId = paymentAttemptId;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getAttemptDate() {
        return this.attemptDate;
    }

    public void setAttemptDate(LocalDateTime attemptDate) {
        this.attemptDate = attemptDate;
    }

}
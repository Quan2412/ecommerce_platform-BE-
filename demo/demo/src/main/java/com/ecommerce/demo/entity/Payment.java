package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private Long paymentId;

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

    @Column(name = "PaymentDate")
    private LocalDateTime paymentDate;

    @OneToMany(mappedBy = "payment")
    private List<Refund> refunds;

    public Payment() {
    }

    public Payment(Long paymentId, Order order, PaymentMethod paymentMethod, Double amount, Currency currency, Double exchangeRate, String status, String transactionId, LocalDateTime paymentDate, List<Refund> refunds) {
        this.paymentId = paymentId;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.status = status;
        this.transactionId = transactionId;
        this.paymentDate = paymentDate;
        this.refunds = refunds;
    }

    public Long getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
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

    public LocalDateTime getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public List<Refund> getRefunds() {
        return this.refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

}
package com.ecommerce.demo.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private Integer cartId;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "AddedAt")
    private LocalDateTime addedAt;


    public Cart() {
    }


    public Cart(Integer cartId, User user, Product product, Integer quantity, LocalDateTime addedAt) {
        this.cartId = cartId;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.addedAt = addedAt;
    }

    /**
     * @return Integer return the cartId
     */

    public Integer getCartId(){
        return cartId;
    }

    /**
     * @param cartId the cartId to set
     */
    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return Integer return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return LocalDateTime return the addedAt
     */
    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    /**
     * @param addedAt the addedAt to set
     */
    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(getCartId(), cart.getCartId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCartId());
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", user=" + user.getUserId() +
                ", product=" + product.getProductId() +
                ", quantity=" + quantity +
                ", addedAt=" + addedAt +
                '}';
    }

    

}
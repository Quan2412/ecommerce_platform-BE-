package com.ecommerce.demo.model;

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
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "AddressLine1", nullable= false, length= 255)
    private String address1;

    @Column(name = "AddressLine2", length = 255)
    private String address2;

    @Column(name = "City", nullable= false)
    private String city;

    @Column(name = "State", length= 100)
    private String state;

    @Column(name = "PostalCode", nullable= false, length= 20)
    private String postalCode;

    @Column(name= "Country", nullable= false, length= 100)
    private String country;

    @Column(name = "IsDefault")
    private Boolean isDefault = false;


    public Address() {
        /*Empty construcotr */
    }
    

    /**
     * @return Integer return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return String return the address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1 the address1 to set
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * @return String return the address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 the address2 to set
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @return String return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return String return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return String return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return String return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return Boolean return the isDefault
     */
    public Boolean isIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

     // equals, hashCode, and toString methods
     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         Address address = (Address) o;
         return Objects.equals(id, address.id);
     }
 
     @Override
     public int hashCode() {
         return Objects.hash(id);
     }
 
     @Override
     public String toString() {
        
         return "Address{" +
                 "addressId=" + id +
                 ", addressLine1='" + address1 + '\'' +
                 ", city='" + city + '\'' +
                 ", country='" + country + '\'' +
                 ", isDefault=" + isDefault +
                 '}';
     }

}
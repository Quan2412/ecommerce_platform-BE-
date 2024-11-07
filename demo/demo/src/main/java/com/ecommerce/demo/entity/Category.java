package com.ecommerce.demo.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer categoriesId;

    @Column(name = "CategoryName", nullable= false, length= 50)
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "ParentCategoryID")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    Set<Category> subCategories = new HashSet<>();

    @OneToMany(mappedBy = "category")
    Set<Product> products = new HashSet<>();


    public Category() {
    }
    

    public Category(Integer categoriesId, String categoryName, Category parentCategory, Set<Category> subCategories, Set<Product> products) {
        this.categoriesId = categoriesId;
        this.categoryName = categoryName;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
        this.products = products;
    }

    

    /**
     * @return Integer return the categoriesId
     */
    public Integer getCategoriesId() {
        return categoriesId;
    }

    /**
     * @param categoriesId the categoriesId to set
     */
    public void setCategoriesId(Integer categoriesId) {
        this.categoriesId = categoriesId;
    }

    /**
     * @return String return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return Category return the parentCategory
     */
    public Category getParentCategory() {
        return parentCategory;
    }

    /**
     * @param parentCategory the parentCategory to set
     */
    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    /**
     * @return Set<Category> return the subCategories
     */
    public Set<Category> getSubCategories() {
        return subCategories;
    }

    /**
     * @param subCategories the subCategories to set
     */
    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }

    /**
     * @return Set<Product> return the products
     */
    public Set<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}